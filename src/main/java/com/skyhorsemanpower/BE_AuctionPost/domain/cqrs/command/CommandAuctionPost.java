package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import com.skyhorsemanpower.BE_AuctionPost.common.BaseCreateAndEndTimeEntity;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String auctionUuid;

    @Column(nullable = false)
    private String sellerUuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int minimumBiddingPrice;

    private String bidderUuid;

    private int bidPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionStateEnum state;


    @Builder
    public CommandAuctionPost(long auctionPostId, String auctionUuid, String sellerUuid, String title, String content, String category, int minimumBiddingPrice, String bidderUuid, int bidPrice, AuctionStateEnum state) {
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