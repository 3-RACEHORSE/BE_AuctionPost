package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import com.skyhorsemanpower.BE_AuctionPost.common.BaseCreateAndEndTimeEntity;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(nullable = false, length = 30)
    private String adminUuid;

    @Column(nullable = false, length = 30)
    private String influencerUuid;

    @Column(nullable = false, length = 30)
    private String influencerName;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, length = 40)
    private String localName;

    @Column(nullable = false, length = 40)
    private String eventPlace;

    @Column(nullable = false, length = 30)
    private LocalDateTime eventStartTime;

    @Column(nullable = false, length = 30)
    private LocalDateTime eventCloseTime;

    @Column(nullable = false, length = 30)
    private LocalDateTime auctionStartTime;

    @Column(nullable = false, length = 10)
    private BigDecimal startPrice;

    @Column(nullable = false, length = 10)
    private BigDecimal incrementUnit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionStateEnum state;


    @Builder
    public CommandAuctionPost(long auctionPostId, String auctionUuid, String adminUuid, String influencerUuid,
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
    }
}