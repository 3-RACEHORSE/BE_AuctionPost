package com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface CustomReadAuctionPostRepository {
    Page<ReadAuctionPost> findAllAuctionPost(SearchAllAuctionPostDto searchAllAuctionDto, Pageable pageable);

    Page<ReadAuctionPost> findAllInfluencerAuctionPost(InfluencerAllAuctionPostDto influencerAllAuctionPostDto, Pageable pageable);
}
