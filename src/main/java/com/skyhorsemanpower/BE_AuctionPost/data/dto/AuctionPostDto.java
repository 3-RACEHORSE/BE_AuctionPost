package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.common.DateTimeConverter;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private LocalDateTime auctionEndTime;
    private BigDecimal startPrice;
    private BigDecimal totalDonation;
    private AuctionStateEnum state;

    private String thumbnail;
    private BigDecimal incrementUnit;

    @Builder
    public AuctionPostDto(String auctionUuid, String influencerUuid, String influencerName,
        String title,
        String localName, String eventPlace, long eventStartTime, AuctionStateEnum state,
        long eventCloseTime, long auctionStartTime, long auctionEndTime,
        BigDecimal startPrice, BigDecimal totalDonation, String thumbnail,
        BigDecimal incrementUnit) {
        this.auctionUuid = auctionUuid;
        this.influencerUuid = influencerUuid;
        this.influencerName = influencerName;
        this.title = title;
        this.localName = localName;
        this.eventPlace = eventPlace;
        this.eventStartTime = DateTimeConverter.instantToLocalDateTime(eventStartTime);
        this.eventCloseTime = DateTimeConverter.instantToLocalDateTime(eventCloseTime);
        this.auctionStartTime = DateTimeConverter.instantToLocalDateTime(auctionStartTime);
        this.auctionEndTime = DateTimeConverter.instantToLocalDateTime(auctionEndTime);
        this.startPrice = startPrice;
        this.totalDonation = totalDonation;
        this.state = state;
        this.thumbnail = thumbnail;
        this.incrementUnit = incrementUnit;
    }
}
