package com.skyhorsemanpower.BE_AuctionPost.kafka.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String influencerUuid;
    private String influencerName;

    @Builder
    public InitialAuctionDto(String auctionUuid, BigDecimal startPrice,
        int numberOfEventParticipants,
        long auctionStartTime, long auctionEndTime, BigDecimal incrementUnit,
        String influencerUuid, String influencerName
    ) {
        this.auctionUuid = auctionUuid;
        this.startPrice = startPrice;
        this.numberOfEventParticipants = numberOfEventParticipants;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.incrementUnit = incrementUnit;
        this.influencerUuid = influencerUuid;
        this.influencerName = influencerName;
    }
}
