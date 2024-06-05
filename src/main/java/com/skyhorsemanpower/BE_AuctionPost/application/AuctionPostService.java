package com.skyhorsemanpower.BE_AuctionPost.application;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;

public interface AuctionPostService {
    void createAuctionPost(CreateAuctionPostDto createAuctionPostDto);
}
