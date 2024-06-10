package com.skyhorsemanpower.BE_AuctionPost.application;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAllAuctionResponseVo;

public interface AuctionPostService {
    void createAuctionPost(CreateAuctionPostDto createAuctionPostDto);

    SearchAllAuctionResponseVo searchAllAuction(SearchAllAuctionDto searchAllAuctionDto);
}
