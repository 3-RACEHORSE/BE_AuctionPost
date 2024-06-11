package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchAuctionPostDto {
    private String auctionUuid;

    @Builder
    public SearchAuctionPostDto(String auctionUuid) {
        this.auctionUuid = auctionUuid;
    }
}
