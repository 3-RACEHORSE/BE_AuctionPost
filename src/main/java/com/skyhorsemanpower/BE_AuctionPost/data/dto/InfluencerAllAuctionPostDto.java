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
public class InfluencerAllAuctionPostDto {
    private String influencerUuid;
    private AuctionStateEnum auctionState;
    private Integer page;
    private Integer size;
}
