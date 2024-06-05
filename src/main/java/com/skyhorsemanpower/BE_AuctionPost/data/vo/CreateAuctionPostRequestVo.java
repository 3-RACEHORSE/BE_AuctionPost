package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
public class CreateAuctionPostRequestVo {
    private String title;
    private String content;
    private String category;
    private BigDecimal minimumPrice;
    private String thumbnail;
    private List<String> images;
}
