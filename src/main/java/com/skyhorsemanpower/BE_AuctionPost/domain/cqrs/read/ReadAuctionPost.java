package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
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
    private String sellerUuid;
    private String title;
    private String content;
    private String category;
    private BigDecimal minimumPrice;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    private String bidderUuid;
    private BigDecimal bidPrice;
    private AuctionStateEnum state;


    @Builder
    public ReadAuctionPost(String auctionPostId, String auctionUuid, String sellerUuid, String title,
                           String content, String category, BigDecimal minimumPrice, LocalDateTime createdAt,
                           LocalDateTime endedAt, String bidderUuid, BigDecimal bidPrice, AuctionStateEnum state) {
        this.auctionPostId = auctionPostId;
        this.auctionUuid = auctionUuid;
        this.sellerUuid = sellerUuid;
        this.title = title;
        this.content = content;
        this.category = category;
        this.minimumPrice = minimumPrice;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.bidderUuid = bidderUuid;
        this.bidPrice = bidPrice;
        this.state = state;
    }

    public void setMinimumPrice(BigDecimal maxPrice) {
        this.minimumPrice = maxPrice;
    }
}