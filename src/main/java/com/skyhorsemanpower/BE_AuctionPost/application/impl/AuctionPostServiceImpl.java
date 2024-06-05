package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.config.QuartzConfig;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.AuctionImages;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.AuctionImagesRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
                            .sellerUuid(createAuctionPostDto.getSellerUuid())
                            .title(createAuctionPostDto.getTitle())
                            .content(createAuctionPostDto.getContent())
                            .category(createAuctionPostDto.getCategory())
                            .minimumBiddingPrice(createAuctionPostDto.getMinimumBiddingPrice())
                            .createdAt(LocalDateTime.now())
                            .endedAt(LocalDateTime.now().plusDays(1))
                            .state(AuctionStateEnum.AUCTION_IS_IN_PROGRESS)
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
                    .sellerUuid(createAuctionPostDto.getSellerUuid())
                    .title(createAuctionPostDto.getTitle())
                    .content(createAuctionPostDto.getContent())
                    .category(createAuctionPostDto.getCategory())
                    .minimumBiddingPrice(createAuctionPostDto.getMinimumBiddingPrice())
                    .state(AuctionStateEnum.AUCTION_IS_IN_PROGRESS)
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
