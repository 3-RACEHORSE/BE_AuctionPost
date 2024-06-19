package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import com.skyhorsemanpower.BE_AuctionPost.common.DateTimeConverter;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.AllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SearchAuctionResponseVo {

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
    private LocalDateTime eventStartTime;
    private LocalDateTime eventCloseTime;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private BigDecimal startPrice;
    private BigDecimal incrementUnit;
    private BigDecimal totalDonation;
    private AuctionStateEnum state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String thumbnail;
    private List<String> images;

    @Builder
    public SearchAuctionResponseVo(AllAuctionPostDto readAuctionPost, String thumbnail,
        List<String> images) {
        this.auctionPostId = readAuctionPost.getAuctionPostId();
        this.auctionUuid = readAuctionPost.getAuctionUuid();
        this.adminUuid = readAuctionPost.getAdminUuid();
        this.influencerUuid = readAuctionPost.getInfluencerUuid();
        this.influencerName = readAuctionPost.getInfluencerName();
        this.title = readAuctionPost.getTitle();
        this.content = readAuctionPost.getContent();
        this.numberOfEventParticipants = readAuctionPost.getNumberOfEventParticipants();
        this.localName = readAuctionPost.getLocalName();
        this.eventPlace = readAuctionPost.getEventPlace();
        this.eventStartTime = DateTimeConverter.instantToLocalDateTime(
            readAuctionPost.getEventStartTime());
        this.eventCloseTime = DateTimeConverter.instantToLocalDateTime(
            readAuctionPost.getEventCloseTime());
        this.auctionStartTime = DateTimeConverter.instantToLocalDateTime(
            readAuctionPost.getAuctionStartTime());
        this.auctionEndTime = DateTimeConverter.instantToLocalDateTime(
            readAuctionPost.getAuctionEndTime());
        this.startPrice = readAuctionPost.getStartPrice();
        this.incrementUnit = readAuctionPost.getIncrementUnit();
        this.totalDonation = readAuctionPost.getTotalDonation();
        this.state = readAuctionPost.getState();
        this.createdAt = DateTimeConverter.instantToLocalDateTime(readAuctionPost.getCreatedAt());
        this.updatedAt = DateTimeConverter.instantToLocalDateTime(readAuctionPost.getUpdatedAt());
        this.thumbnail = thumbnail;
        this.images = images;
    }
}
