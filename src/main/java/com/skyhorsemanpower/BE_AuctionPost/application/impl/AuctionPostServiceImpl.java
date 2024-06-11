package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.config.QuartzConfig;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.AuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAuctionResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.domain.AuctionImages;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.AuctionImagesRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.PageState;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionPostServiceImpl implements AuctionPostService {
    private final CommandAuctionPostRepository commandAuctionPostRepository;
    private final ReadAuctionPostRepository readAuctionPostRepository;
    private final AuctionImagesRepository auctionImagesRepository;
    private final QuartzConfig quartzConfig;

    @Override
    @Transactional
    public void createAuctionPost(CreateAuctionPostDto createAuctionPostDto) {
        // auctionUuid
        String auctionUuid = generateAuctionUuid();
        createAuctionPostDto.setAuctionUuid(auctionUuid);

        // PostgreSQL 저장
        createCommandAutionPost(createAuctionPostDto);
        createAuctionImages(createAuctionPostDto);

        // MongoDB 저장
        createReadAuctionPost(createAuctionPostDto);

        // 스케줄러에 경매 마감 등록
        createScheduler(auctionUuid);
    }

    @Override
    @Transactional
    public SearchAllAuctionPostResponseVo searchAllAuction(SearchAllAuctionPostDto searchAllAuctionDto) {
        // 입력 auctionState가 없는 경우는 진행 중인 것으로 판단한다.
        if (searchAllAuctionDto.getAuctionState() == null)
            searchAllAuctionDto.setAuctionState(AuctionStateEnum.AUCTION_IS_IN_PROGRESS);

        Integer page = searchAllAuctionDto.getPage();
        Integer size = searchAllAuctionDto.getSize();

        // page, size 미지정 시, 기본값 할당
        if (page == null || page < 0) page = PageState.DEFAULT.getPage();
        if (size == null || size <= 0) size = PageState.DEFAULT.getSize();

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

        for(ReadAuctionPost readAuctionPost : auctionPosts) {
            String thumbnail = auctionImagesRepository.getThumbnailUrl(
                    readAuctionPost.getAuctionUuid());

            log.info("thumbnail >>> {}", thumbnail);

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
                            .startPrice(readAuctionPost.getStartPrice())
                            .thumbnail(thumbnail)
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
        return SearchAuctionResponseVo.builder()
                .readAuctionPost(readAuctionPost)
                .thumbnail(auctionImagesRepository.getThumbnailUrl(searchAuctionPostDto.getAuctionUuid()))
                .images(auctionImagesRepository.getImagesUrl(searchAuctionPostDto.getAuctionUuid()))
                .build();
    }


    private void createScheduler(String auctionUuid) {
        try {
            quartzConfig.schedulerEndAuctionJob(auctionUuid);
        } catch (SchedulerException e) {
            log.info("Scheduler exception for auction UUID: {}", auctionUuid, e);
            throw new CustomException(ResponseStatus.SCHEDULER_ERROR);
        }
    }

    private void createReadAuctionPost(CreateAuctionPostDto createAuctionPostDto) {
        try {
            readAuctionPostRepository.save(
                    ReadAuctionPost.builder()
                            .auctionUuid(createAuctionPostDto.getAuctionUuid())
                            .adminUuid(createAuctionPostDto.getAdminUuid())
                            .influencerUuid(createAuctionPostDto.getInfluencerUuid())
                            .influencerName(createAuctionPostDto.getInfluencerName())
                            .title(createAuctionPostDto.getTitle())
                            .content(createAuctionPostDto.getContent())
                            .localName(createAuctionPostDto.getLocalName())
                            .eventPlace(createAuctionPostDto.getEventPlace())
                            .eventStartTime(createAuctionPostDto.getEventStartTime())
                            .eventCloseTime(createAuctionPostDto.getEventCloseTime())
                            .auctionStartTime(createAuctionPostDto.getAuctionStartTime())
                            .startPrice(createAuctionPostDto.getStartPrice())
                            .incrementUnit(createAuctionPostDto.getIncrementUnit())
                            .state(AuctionStateEnum.BEFORE_AUCTION)
                            .build()
            );
        } catch (Exception e) {
            log.info("Create Read AuctionPost Error", e);
            throw new CustomException(ResponseStatus.MONGODB_ERROR);
        }
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

    private void createCommandAutionPost(CreateAuctionPostDto createAuctionPostDto) {
        try {
            commandAuctionPostRepository.save(CommandAuctionPost.builder()
                    .auctionUuid(createAuctionPostDto.getAuctionUuid())
                    .adminUuid(createAuctionPostDto.getAdminUuid())
                    .influencerUuid(createAuctionPostDto.getInfluencerUuid())
                    .influencerName(createAuctionPostDto.getInfluencerName())
                    .title(createAuctionPostDto.getTitle())
                    .content(createAuctionPostDto.getContent())
                    .localName(createAuctionPostDto.getLocalName())
                    .eventPlace(createAuctionPostDto.getEventPlace())
                    .eventStartTime(createAuctionPostDto.getEventStartTime())
                    .eventCloseTime(createAuctionPostDto.getEventCloseTime())
                    .auctionStartTime(createAuctionPostDto.getAuctionStartTime())
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

}
