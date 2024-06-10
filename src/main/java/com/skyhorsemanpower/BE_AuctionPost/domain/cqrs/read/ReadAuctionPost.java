package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@Document(collection = "auction_post")
public class ReadAuctionPost {

    @Id
    private String auctionPostId;

    private String auctionUuid;
    private String adminUuid;
    private String influencerUuid;
    private String influencerName;
    private String title;
    private String content;
    private String localName;
    private String eventPlace;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventCloseTime;
    private LocalDateTime auctionStartTime;
    private BigDecimal startPrice;
    private BigDecimal incrementUnit;
    private AuctionStateEnum state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ReadAuctionPost(String auctionPostId, String auctionUuid, String adminUuid, String influencerUuid,
                           String influencerName, String title, String content, String localName, String eventPlace,
                           LocalDateTime eventStartTime, LocalDateTime eventCloseTime, LocalDateTime auctionStartTime,
                           BigDecimal startPrice, BigDecimal incrementUnit, AuctionStateEnum state) {
        this.auctionPostId = auctionPostId;
        this.auctionUuid = auctionUuid;
        this.adminUuid = adminUuid;
        this.influencerUuid = influencerUuid;
        this.influencerName = influencerName;
        this.title = title;
        this.content = content;
        this.localName = localName;
        this.eventPlace = eventPlace;
        this.eventStartTime = eventStartTime;
        this.eventCloseTime = eventCloseTime;
        this.auctionStartTime = auctionStartTime;
        this.startPrice = startPrice;
        this.incrementUnit = incrementUnit;
        this.state = state;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}