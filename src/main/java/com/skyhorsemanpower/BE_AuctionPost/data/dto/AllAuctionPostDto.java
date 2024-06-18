package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllAuctionPostDto {

    private String auctionPostId;
    private String auctionUuid;
    private String adminUuid;
    private String influencerUuid;
    private String influencerName;
    private String title;
    private String content;
    private int numberOfEventParticipants;
    private String localName;
    private String eventPlace;
    private long eventStartTime;
    private long eventCloseTime;
    private long auctionStartTime;
    private long auctionEndTime;
    private BigDecimal startPrice;
    private BigDecimal incrementUnit;
    private BigDecimal totalDonation;
    private AuctionStateEnum state;
    private long createdAt;
    private long updatedAt;

}
