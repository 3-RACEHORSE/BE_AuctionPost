package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import com.skyhorsemanpower.BE_AuctionPost.common.BaseCreateAndEndTimeEntity;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    @Column(nullable = false, length = 40)
    private String sellerUuid;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, length = 30)
    private String category;

    @Column(nullable = false, length = 10)
    private BigDecimal minimumBiddingPrice;

    @Column(length = 40)
    private String bidderUuid;

    @Column(length = 10)
    private BigDecimal bidPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionStateEnum state;


    @Builder
    public CommandAuctionPost(long auctionPostId, String auctionUuid, String sellerUuid, String title,
                              String content, String category, BigDecimal minimumBiddingPrice, String bidderUuid,
                              BigDecimal bidPrice, AuctionStateEnum state) {
        this.auctionPostId = auctionPostId;
        this.auctionUuid = auctionUuid;
        this.sellerUuid = sellerUuid;
        this.title = title;
        this.content = content;
        this.category = category;
        this.minimumBiddingPrice = minimumBiddingPrice;
        this.bidderUuid = bidderUuid;
        this.bidPrice = bidPrice;
        this.state = state;
    }
}