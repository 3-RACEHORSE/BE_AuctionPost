package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.common.DateTimeConverter;
import com.skyhorsemanpower.BE_AuctionPost.common.StringToBigDecimalConverter;
import com.skyhorsemanpower.BE_AuctionPost.config.QuartzJobConfig;
import com.skyhorsemanpower.BE_AuctionPost.kafkac.Topics;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.AllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.AuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.MainPagePostResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.MainPagePostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAuctionResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.UpdateTotalDonationUpdateVo;
import com.skyhorsemanpower.BE_AuctionPost.domain.AuctionImages;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.kafkac.KafkaProducerCluster;
import com.skyhorsemanpower.BE_AuctionPost.kafkac.dto.InitialAuctionDto;
import com.skyhorsemanpower.BE_AuctionPost.repository.AuctionImagesRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionEndTimeState;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionLimitTimeEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionPostFilteringEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.PageState;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionPostServiceImpl implements AuctionPostService {

	private final CommandAuctionPostRepository commandAuctionPostRepository;
	private final ReadAuctionPostRepository readAuctionPostRepository;
	private final AuctionImagesRepository auctionImagesRepository;
	private final QuartzJobConfig quartzJobConfig;
	private final KafkaProducerCluster producer;

	@Override
	@Transactional
	public void createAuctionPost(CreateAuctionPostDto createAuctionPostDto) {
		// 경매 시작 시간 제한
		if (DateTimeConverter.instantToLocalDateTime(createAuctionPostDto.getAuctionStartTime())
			.toLocalTime()
			.isAfter(AuctionLimitTimeEnum.BANK_CHECK.getTime())) {

			throw new CustomException(ResponseStatus.BANK_CHECK_TIME);
		}

		// auctionUuid
		String auctionUuid = generateAuctionUuid();
		createAuctionPostDto.setAuctionUuid(auctionUuid);

		// PostgreSQL 저장
		createCommandAuctionPost(createAuctionPostDto);
		createAuctionImages(createAuctionPostDto);

		startAuctionJobSchedule(auctionUuid, createAuctionPostDto.getAuctionStartTime());

		InitialAuctionDto initialAuctionDto = InitialAuctionDto.builder()
			.auctionUuid(auctionUuid)
			.startPrice(createAuctionPostDto.getStartPrice())
			.numberOfEventParticipants(createAuctionPostDto.getNumberOfEventParticipants())
			.auctionStartTime(createAuctionPostDto.getAuctionStartTime())
			.auctionEndTime(Instant.ofEpochMilli(createAuctionPostDto.getAuctionStartTime())
				.plus(Duration.ofHours(AuctionEndTimeState.TWO_HOUR.getEndTime()))
				.toEpochMilli())
			.build();

		producer.sendMessage(Topics.Constant.INITIAL_AUCTION, initialAuctionDto);
	}

	private void startAuctionJobSchedule(String auctionUuid, long auctionStartTime) {
		try {
			quartzJobConfig.schedulerStartAuctionJob(auctionUuid, auctionStartTime);
		} catch (SchedulerException e) {
			log.info("Scheduler exception for auction UUID: {}", auctionUuid, e);
			throw new CustomException(ResponseStatus.SCHEDULER_ERROR);
		}
	}

	@Override
	@Transactional
	public SearchAllAuctionPostResponseVo searchAllAuction(
		SearchAllAuctionPostDto searchAllAuctionDto) {
		// 입력 auctionState가 없는 경우는 모든 경매를 조회한다.
		if (searchAllAuctionDto.getAuctionState() == null) {
			searchAllAuctionDto.setAuctionState(AuctionPostFilteringEnum.ALL_AUCTION);
		}

		Integer page = searchAllAuctionDto.getPage();
		Integer size = searchAllAuctionDto.getSize();

		// page, size 미지정 시, 기본값 할당
		if (page == null || page < 0) {
			page = PageState.DEFAULT.getPage();
		}
		if (size == null || size <= 0) {
			size = PageState.DEFAULT.getSize();
		}

		Page<ReadAuctionPost> readAuctionPostPage = readAuctionPostRepository.findAllAuctionPost(
			searchAllAuctionDto, PageRequest.of(page, size)
		);

		// 조회 없는 경우 예외 처리
		if (readAuctionPostPage.isEmpty()) {
			log.info("Search Auction result is Empty");
			throw new CustomException(ResponseStatus.NO_DATA);
		}

		List<ReadAuctionPost> auctionPosts = readAuctionPostPage.getContent();

		// Vo에 들어가는 데이터로 변환
		List<AuctionPostDto> auctionPostDtos = new ArrayList<>();

		for (ReadAuctionPost readAuctionPost : auctionPosts) {

			// exception을 던질 수 있는 메서드를 먼저 실행
			BigDecimal startPrice = StringToBigDecimalConverter.convert(
				readAuctionPost.getStartPrice());
			BigDecimal totalDonation = StringToBigDecimalConverter.convert(
				readAuctionPost.getTotalDonation());
			BigDecimal incrementUnit = StringToBigDecimalConverter.convert(
				readAuctionPost.getIncrementUnit());

			String thumbnail = auctionImagesRepository.getThumbnailUrl(
				readAuctionPost.getAuctionUuid());

			auctionPostDtos.add(AuctionPostDto.builder()
				.auctionUuid(readAuctionPost.getAuctionUuid())
				.influencerUuid(readAuctionPost.getInfluencerUuid())
				.influencerName(readAuctionPost.getInfluencerName())
				.title(readAuctionPost.getTitle())
				.localName(readAuctionPost.getLocalName())
				.eventPlace(readAuctionPost.getEventPlace())
				.eventStartTime(readAuctionPost.getEventStartTime())
				.eventCloseTime(readAuctionPost.getEventCloseTime())
				.auctionStartTime(readAuctionPost.getAuctionStartTime())
				.auctionEndTime(readAuctionPost.getAuctionEndTime())
				.startPrice(startPrice)
				.totalDonation(totalDonation)
				.state(readAuctionPost.getState())
				.thumbnail(thumbnail)
				.incrementUnit(incrementUnit)
				.build());
		}

		boolean hasNext = readAuctionPostPage.hasNext();

		return SearchAllAuctionPostResponseVo.builder()
			.auctionPostDtos(auctionPostDtos)
			.currentPage(page)
			.hasNext(hasNext)
			.build();
	}

	@Override
	public SearchAuctionResponseVo searchAuctionPost(SearchAuctionPostDto searchAuctionPostDto) {
		ReadAuctionPost readAuctionPost = readAuctionPostRepository.findByAuctionUuid(
			searchAuctionPostDto.getAuctionUuid()).orElseThrow(
			() -> new CustomException(ResponseStatus.NO_DATA)
		);

		BigDecimal startPrice = StringToBigDecimalConverter.convert(
			readAuctionPost.getStartPrice());
		BigDecimal totalDonation = StringToBigDecimalConverter.convert(
			readAuctionPost.getTotalDonation());
		BigDecimal incrementUnit = StringToBigDecimalConverter.convert(
			readAuctionPost.getIncrementUnit());

		return SearchAuctionResponseVo.builder()
			.readAuctionPost(AllAuctionPostDto.builder()
				.auctionPostId(readAuctionPost.getAuctionPostId())
				.auctionUuid(readAuctionPost.getAuctionUuid())
				.adminUuid(readAuctionPost.getAdminUuid())
				.influencerUuid(readAuctionPost.getInfluencerUuid())
				.influencerName(readAuctionPost.getInfluencerName())
				.title(readAuctionPost.getTitle())
				.content(readAuctionPost.getContent())
				.numberOfEventParticipants(readAuctionPost.getNumberOfEventParticipants())
				.localName(readAuctionPost.getLocalName())
				.eventPlace(readAuctionPost.getEventPlace())
				.eventStartTime(readAuctionPost.getEventStartTime())
				.eventCloseTime(readAuctionPost.getEventCloseTime())
				.auctionStartTime(readAuctionPost.getAuctionStartTime())
				.auctionEndTime(readAuctionPost.getAuctionEndTime())
				.startPrice(startPrice)
				.incrementUnit(incrementUnit)
				.totalDonation(totalDonation)
				.state(readAuctionPost.getState())
				.createdAt(readAuctionPost.getCreatedAt())
				.updatedAt(readAuctionPost.getUpdatedAt())
				.build())
			.thumbnail(
				auctionImagesRepository.getThumbnailUrl(searchAuctionPostDto.getAuctionUuid()))
			.images(auctionImagesRepository.getImagesUrl(searchAuctionPostDto.getAuctionUuid()))
			.build();
	}

	@Override
	public InfluencerAllAuctionPostResponseVo influencerAuctionPost(
		InfluencerAllAuctionPostDto influencerAllAuctionPostDto) {
		// 입력 auctionState가 없는 경우는 진행 중인 것으로 판단한다.
		if (influencerAllAuctionPostDto.getAuctionState() == null) {
			influencerAllAuctionPostDto.setAuctionState(AuctionStateEnum.AUCTION_IS_IN_PROGRESS);
		}

		Integer page = influencerAllAuctionPostDto.getPage();
		Integer size = influencerAllAuctionPostDto.getSize();

		// page, size 미지정 시, 기본값 할당
		if (page == null || page < 0) {
			page = PageState.DEFAULT.getPage();
		}
		if (size == null || size <= 0) {
			size = PageState.DEFAULT.getSize();
		}

		Page<ReadAuctionPost> readAuctionPostPage = readAuctionPostRepository.findAllInfluencerAuctionPost(
			influencerAllAuctionPostDto, PageRequest.of(page, size)
		);

		// 조회 없는 경우 예외 처리
		if (readAuctionPostPage.isEmpty()) {
			log.info("Search Influencer Auction result is Empty");
			throw new CustomException(ResponseStatus.NO_DATA);
		}

		List<ReadAuctionPost> auctionPosts = readAuctionPostPage.getContent();

		// Vo에 들어가는 데이터로 변환
		List<AuctionPostDto> auctionPostDtos = new ArrayList<>();

		for (ReadAuctionPost readAuctionPost : auctionPosts) {
			BigDecimal startPrice = StringToBigDecimalConverter.convert(
				readAuctionPost.getStartPrice());
			BigDecimal totalDonation = StringToBigDecimalConverter.convert(
				readAuctionPost.getTotalDonation());
			BigDecimal incrementUnit = StringToBigDecimalConverter.convert(
				readAuctionPost.getIncrementUnit());

			String thumbnail = auctionImagesRepository.getThumbnailUrl(
				readAuctionPost.getAuctionUuid());

			auctionPostDtos.add(AuctionPostDto.builder()
				.auctionUuid(readAuctionPost.getAuctionUuid())
				.influencerUuid(readAuctionPost.getInfluencerUuid())
				.influencerName(readAuctionPost.getInfluencerName())
				.title(readAuctionPost.getTitle())
				.localName(readAuctionPost.getLocalName())
				.eventPlace(readAuctionPost.getEventPlace())
				.eventStartTime(readAuctionPost.getEventStartTime())
				.eventCloseTime(readAuctionPost.getEventCloseTime())
				.auctionStartTime(readAuctionPost.getAuctionStartTime())
				.auctionEndTime(readAuctionPost.getAuctionEndTime())
				.startPrice(startPrice)
				.totalDonation(totalDonation)
				.state(readAuctionPost.getState())
				.thumbnail(thumbnail)
				.incrementUnit(incrementUnit)
				.build());
		}

		boolean hasNext = readAuctionPostPage.hasNext();

		return InfluencerAllAuctionPostResponseVo.builder()
			.auctionPostDtos(auctionPostDtos)
			.currentPage(page)
			.hasNext(hasNext)
			.build();
	}

	private void createAuctionImages(CreateAuctionPostDto createAuctionPostDto) {
		try {
			// 썸네일 저장
			auctionImagesRepository.save(
				AuctionImages.builder()
					.auctionUuid(createAuctionPostDto.getAuctionUuid())
					.imageUrl(createAuctionPostDto.getThumbnail())
					.isThumbnail(true)
					.build()
			);

			// 일반 이미지 저장
			List<String> images = createAuctionPostDto.getImages();

			for (String image : images) {
				auctionImagesRepository.save(
					AuctionImages.builder()
						.auctionUuid(createAuctionPostDto.getAuctionUuid())
						.imageUrl(image)
						.isThumbnail(false)
						.build()
				);
			}
		} catch (Exception e) {
			log.info("Create Auction Image Error", e);
			throw new CustomException(ResponseStatus.POSTGRESQL_ERROR);
		}
	}

	private void createCommandAuctionPost(CreateAuctionPostDto createAuctionPostDto) {
		try {
			commandAuctionPostRepository.save(CommandAuctionPost.builder()
				.auctionUuid(createAuctionPostDto.getAuctionUuid())
				.adminUuid(createAuctionPostDto.getAdminUuid())
				.influencerUuid(createAuctionPostDto.getInfluencerUuid())
				.influencerName(createAuctionPostDto.getInfluencerName())
				.title(createAuctionPostDto.getTitle())
				.content(createAuctionPostDto.getContent())
				.numberOfEventParticipants(createAuctionPostDto.getNumberOfEventParticipants())
				.localName(createAuctionPostDto.getLocalName())
				.eventPlace(createAuctionPostDto.getEventPlace())
				.eventStartTime(createAuctionPostDto.getEventStartTime())
				.eventCloseTime(createAuctionPostDto.getEventCloseTime())
				.auctionStartTime(createAuctionPostDto.getAuctionStartTime())
				.auctionEndTime(
					Instant.ofEpochMilli(createAuctionPostDto.getAuctionStartTime())
						.plus(Duration.ofHours(AuctionEndTimeState.TWO_HOUR.getEndTime()))
						.toEpochMilli()
				)
				.startPrice(createAuctionPostDto.getStartPrice())
				.incrementUnit(createAuctionPostDto.getIncrementUnit())
				.state(AuctionStateEnum.BEFORE_AUCTION)
				.build());
		} catch (Exception e) {
			log.info("Create Command AuctionPost Error", e);
			throw new CustomException(ResponseStatus.POSTGRESQL_ERROR);
		}
	}


	private String generateAuctionUuid() {
		// 현재 날짜와 시간을 "yyyyMMddHHmm" 형식으로 포맷
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String dateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);

		// UUID 생성 후 앞부분의 10자리 문자열 추출
		String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

		// 날짜 시간과 UUID의 앞부분을 합쳐 UUID 생성
		return dateTime + "-" + uuid;
	}

	@Override
	@Transactional
	public void updateTotalDonationAmount(UpdateTotalDonationUpdateVo updateTotalDonationUpdateVo) {
		CommandAuctionPost auctionPost = commandAuctionPostRepository.findByAuctionUuid(
			updateTotalDonationUpdateVo.getAuctionUuid()).orElseThrow(
			() -> new CustomException(ResponseStatus.NO_DATA)
		);
		try {
			commandAuctionPostRepository.save(
				CommandAuctionPost.builder()
					.auctionPostId(auctionPost.getAuctionPostId())
					.auctionUuid(auctionPost.getAuctionUuid())
					.adminUuid(auctionPost.getAdminUuid())
					.influencerUuid(auctionPost.getInfluencerUuid())
					.influencerName(auctionPost.getInfluencerName())
					.title(auctionPost.getTitle())
					.content(auctionPost.getContent())
					.numberOfEventParticipants(auctionPost.getNumberOfEventParticipants())
					.localName(auctionPost.getLocalName())
					.eventPlace(auctionPost.getEventPlace())
					.eventStartTime(auctionPost.getEventStartTime())
					.eventCloseTime(auctionPost.getEventCloseTime())
					.auctionStartTime(auctionPost.getAuctionStartTime())
					.auctionEndTime(auctionPost.getAuctionEndTime())
					.startPrice(auctionPost.getStartPrice())
					.incrementUnit(auctionPost.getIncrementUnit())
					.totalDonation(updateTotalDonationUpdateVo.getTotalDonationAmount())
					.state(auctionPost.getState())
					.build()
			);
		} catch (Exception e) {
			log.info("Update Total Donation Amount Error", e);
			throw new CustomException(ResponseStatus.POSTGRESQL_ERROR);
		}
	}

	@Override
	public List<MainPagePostResponseVo> mainPagePost() {
		List<MainPagePostResponseDto> mainPagePostResponseDtoList = new ArrayList<>();

		Pageable limit = PageRequest.of(0, 10);

		Page<ReadAuctionPost> inProgress = readAuctionPostRepository.findByState(
			AuctionStateEnum.AUCTION_IS_IN_PROGRESS.toString(), limit);
		List<ReadAuctionPost> result = new ArrayList<>(inProgress.getContent());

		if (result.size() < 10) {
			int remain = 10 - result.size();
			Pageable remainLimit = PageRequest.of(0, remain);
			result.addAll(readAuctionPostRepository.findByState(
				AuctionStateEnum.BEFORE_AUCTION.toString(), remainLimit).getContent());
		}

		result.stream()
			.map(readAuctionPost -> {
				String thumbnail = auctionImagesRepository.getThumbnailUrl(
					readAuctionPost.getAuctionUuid());
				return MainPagePostResponseDto.builder()
					.auctionUuid(readAuctionPost.getAuctionUuid())
					.title(readAuctionPost.getTitle())
					.eventStartTime(readAuctionPost.getEventStartTime())
					.state(readAuctionPost.getState())
					.thumbnail(thumbnail)
					.build();
			})
			.forEach(mainPagePostResponseDtoList::add);

		List<MainPagePostResponseVo> mainPagePostResponseVoList = new ArrayList<>();

		mainPagePostResponseDtoList.stream()
			.map(mainPagePostResponseDto ->
				MainPagePostResponseVo.builder()
					.auctionUuid(mainPagePostResponseDto.getAuctionUuid())
					.title(mainPagePostResponseDto.getTitle())
					.eventStartTime(mainPagePostResponseDto.getEventStartTime())
					.state(mainPagePostResponseDto.getState())
					.thumbnail(mainPagePostResponseDto.getThumbnail())
					.build())
			.forEach(mainPagePostResponseVoList::add);

		return mainPagePostResponseVoList;
	}
}
