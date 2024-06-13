package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchAuctionPostTitleAndInfluencerNameResponseVo {
    private List<String> result;
}
