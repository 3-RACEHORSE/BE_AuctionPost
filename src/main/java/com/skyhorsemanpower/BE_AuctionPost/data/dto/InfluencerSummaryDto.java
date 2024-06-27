package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InfluencerSummaryDto {
    private String name;
    private String profileImage;
}
