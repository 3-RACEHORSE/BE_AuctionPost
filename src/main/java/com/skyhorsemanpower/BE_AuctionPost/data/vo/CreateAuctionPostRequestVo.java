package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class CreateAuctionPostRequestVo {
    private String auctionUuid;
    private String influencerUuid;
    private String title;
    private String content;
    private String eventPlace;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventCloseTime;
    private LocalDateTime auctionStartTime;
    private BigDecimal startPrice;
    private BigDecimal incrementUnit;

    private String thumbnail;
    private List<String> images;
}
