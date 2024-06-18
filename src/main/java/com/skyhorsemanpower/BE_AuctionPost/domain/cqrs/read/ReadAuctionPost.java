package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Getter
@ToString
@Document(collection = "auction_post")
public class ReadAuctionPost {

    @Id
    @Field(name = "auction_post_id")
    private String auctionPostId;
    @Field(name = "auction_uuid")
    private String auctionUuid;
    @Field(name = "admin_uuid")
    private String adminUuid;
    @Field(name = "influencer_uuid")
    private String influencerUuid;
    @Field(name = "influencer_name")
    private String influencerName;
    @Field(name = "title")
    private String title;
    @Field(name = "content")
    private String content;
    @Field(name = "number_of_event_participants")
    private int numberOfEventParticipants;
    @Field(name = "local_name")
    private String localName;
    @Field(name = "event_place")
    private String eventPlace;
    @Field(name = "event_start_time")
    private long eventStartTime;
    @Field(name = "event_close_time")
    private long eventCloseTime;
    @Field(name = "auction_start_time")
    private long auctionStartTime;
    @Field(name = "auction_end_time")
    private long auctionEndTime;
    @Field(name = "start_price")
    private BigDecimal startPrice;
    @Field(name = "increment_unit")
    private BigDecimal incrementUnit;
    @Field(name = "total_donation")
    private BigDecimal totalDonation;
    @Field(name = "state")
    private AuctionStateEnum state;
    @Field(name = "created_at")
    private long createdAt;
    @Field(name = "updated_at")
    private long updatedAt;

    @Builder
    public ReadAuctionPost(String auctionPostId, String auctionUuid, String adminUuid,
        String influencerUuid,
        String influencerName, String title, String content, int numberOfEventParticipants,
        String localName, String eventPlace, long eventStartTime,
        long auctionEndTime, long eventCloseTime, long auctionStartTime,
        BigDecimal startPrice, BigDecimal incrementUnit, BigDecimal totalDonation,
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