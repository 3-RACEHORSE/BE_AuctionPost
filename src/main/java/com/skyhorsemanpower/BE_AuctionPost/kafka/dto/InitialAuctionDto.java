package com.skyhorsemanpower.BE_AuctionPost.kafka.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionEndTimeState;
import com.skyhorsemanpower.BE_AuctionPost.status.TimeZoneChangeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class InitialAuctionDto {
    private String auctionUuid;
    private BigDecimal startPrice;
    private int numberOfEventParticipants;
    private long auctionStartTime;
    private long auctionEndTime;
    private BigDecimal incrementUnit;

    @Builder
    public InitialAuctionDto(String auctionUuid, BigDecimal startPrice, int numberOfEventParticipants,
                             long auctionStartTime, long auctionEndTime, BigDecimal incrementUnit) {
        this.auctionUuid = auctionUuid;
        this.startPrice = startPrice;
        this.numberOfEventParticipants = numberOfEventParticipants;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.incrementUnit = incrementUnit;
    }

    //todo
    // CDC 로직에 맞춰서 바뀔 가능성 존재
//    public static InitialAuctionDto converter(CreateAuctionPostDto createAuctionPostDto) {
//        return InitialAuctionDto.builder()
//                .auctionUuid(createAuctionPostDto.getAuctionUuid())
//                .startPrice(createAuctionPostDto.getStartPrice())
//                .numberOfEventParticipants(createAuctionPostDto.getNumberOfEventParticipants())
//                .auctionStartTime(createAuctionPostDto.getAuctionStartTime()
//                        .plusHours(TimeZoneChangeEnum.KOREA_TO_UTC.getTimeDiff()))
//                .auctionEndTime(createAuctionPostDto.getEventCloseTime()
//                        .plusHours(TimeZoneChangeEnum.KOREA_TO_UTC.getTimeDiff())
//                        .plusHours(AuctionEndTimeState.TWO_HOUR.getEndTime()))
//                .incrementUnit(createAuctionPostDto.getIncrementUnit())
//                .build();
//    }
}
