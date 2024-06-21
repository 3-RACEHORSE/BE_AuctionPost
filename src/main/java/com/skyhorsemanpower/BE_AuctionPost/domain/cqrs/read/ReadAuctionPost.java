package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private int numberOfEventParticipants;
    private String localName;
    private String eventPlace;
    private long eventStartTime;
    private long eventCloseTime;
    private long auctionStartTime;
    private long auctionEndTime;
    private String startPrice;
    private String incrementUnit;
    private String totalDonation;
    private AuctionStateEnum state;
    private long createdAt;
    private long updatedAt;

    @Builder
    public ReadAuctionPost(String auctionPostId, String auctionUuid, String adminUuid,
        String influencerUuid,
        String influencerName, String title, String content, int numberOfEventParticipants,
        String localName, String eventPlace, long eventStartTime,
        long auctionEndTime, long eventCloseTime, long auctionStartTime,
        String startPrice, String incrementUnit, String totalDonation,
        AuctionStateEnum state) {
        this.auctionPostId = auctionPostId;
        this.auctionUuid = auctionUuid;
        this.adminUuid = adminUuid;
        this.influencerUuid = influencerUuid;
        this.influencerName = influencerName;
        this.title = title;
        this.content = content;
        this.numberOfEventParticipants = numberOfEventParticipants;
        this.localName = localName;
        this.eventPlace = eventPlace;
        this.eventStartTime = eventStartTime;
        this.eventCloseTime = eventCloseTime;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.startPrice = startPrice;
        this.incrementUnit = incrementUnit;
        this.totalDonation = totalDonation;
        this.state = state;
        this.createdAt = Instant.now().toEpochMilli();
        this.updatedAt = Instant.now().toEpochMilli();
    }
}