package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.AuctionPostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class SearchAllAuctionPostResponseVo {
    private List<AuctionPostDto> auctionPostDtos;
    private int currentPage;
    private boolean hasNext;

    @Builder
    public SearchAllAuctionPostResponseVo(List<AuctionPostDto> auctionPostDtos, int currentPage, boolean hasNext) {
        this.auctionPostDtos = auctionPostDtos;
        this.currentPage = currentPage;
        this.hasNext = hasNext;
    }
}
