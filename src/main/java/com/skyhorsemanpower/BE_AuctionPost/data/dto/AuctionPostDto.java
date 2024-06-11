package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionPostDto {
    private String auctionUuid;
    private String influencerUuid;
    private String influencerName;
    private String title;
    private String localName;
    private String eventPlace;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventCloseTime;
    private LocalDateTime auctionStartTime;
    private BigDecimal startPrice;
    private BigDecimal totalDonation;

    private String thumbnail;

    @Builder
    public AuctionPostDto(String auctionUuid, String influencerUuid, String influencerName, String title,
                          String localName, String eventPlace, LocalDateTime eventStartTime,
                          LocalDateTime eventCloseTime, LocalDateTime auctionStartTime,
                          BigDecimal startPrice, BigDecimal totalDonation, String thumbnail) {
        this.auctionUuid = auctionUuid;
        this.influencerUuid = influencerUuid;
        this.influencerName = influencerName;
        this.title = title;
        this.localName = localName;
        this.eventPlace = eventPlace;
        this.eventStartTime = eventStartTime;
        this.eventCloseTime = eventCloseTime;
        this.auctionStartTime = auctionStartTime;
        this.startPrice = startPrice;
        this.totalDonation = totalDonation;
        this.thumbnail = thumbnail;
    }
}
