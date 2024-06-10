package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SearchAllAuctionDto {
    private String uuid;
    private String title;
    private String influencerName;
    private AuctionStateEnum auctionState;
    private String localName;
    private Integer page;
    private Integer size;
}
