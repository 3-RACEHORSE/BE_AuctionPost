package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import com.skyhorsemanpower.BE_AuctionPost.common.BaseCreateAndEndTimeEntity;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "auction_post")
public class CommandAuctionPost extends BaseCreateAndEndTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long auctionPostId;

    @Column(nullable = false, length = 30)
    private String auctionUuid;

    @Column(nullable = false, length = 50)
    private String adminUuid;

    @Column(nullable = false, length = 50)
    private String influencerUuid;

    @Column(nullable = false, length = 100)
    private String influencerName;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, columnDefinition = "integer default 200")
    private int numberOfEventParticipants;

    @Column(nullable = false, length = 40)
    private String localName;

    @Column(nullable = false, length = 40)
    private String eventPlace;

    @Column(nullable = false, length = 30)
    private long eventStartTime;

    @Column(nullable = false, length = 30)
    private long eventCloseTime;

    @Column(nullable = false, length = 30)
    private long auctionStartTime;

    @Column(nullable = false, length = 30)
    private long auctionEndTime;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal startPrice;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal incrementUnit;

    @Column(nullable = true, precision = 20, scale = 2)
    private BigDecimal totalDonation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionStateEnum state;


    @Builder
    public CommandAuctionPost(long auctionPostId, String auctionUuid, String adminUuid,
        String influencerUuid,
        String influencerName, String title, String content, int numberOfEventParticipants,
        String localName, String eventPlace, long eventStartTime,
        long auctionEndTime, long eventCloseTime,
        long auctionStartTime, BigDecimal startPrice, BigDecimal incrementUnit,
        BigDecimal totalDonation, AuctionStateEnum state) {
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
    }
}