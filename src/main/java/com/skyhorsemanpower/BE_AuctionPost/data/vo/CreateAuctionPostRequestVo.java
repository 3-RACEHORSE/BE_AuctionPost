package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CreateAuctionPostRequestVo {
    private String title;
    private String content;
    private String category;
    private int minimumBiddingPrice;
    private String thumbnail;
    private List<String> images;
}
