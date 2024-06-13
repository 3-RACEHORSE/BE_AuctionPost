package com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostTitleDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAuctionPostTitleAndInfluencerNameResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomReadAuctionPostRepository {
    Page<ReadAuctionPost> findAllAuctionPost(SearchAllAuctionPostDto searchAllAuctionDto, Pageable pageable);

    Page<ReadAuctionPost> findAllInfluencerAuctionPost(InfluencerAllAuctionPostDto influencerAllAuctionPostDto, Pageable pageable);

    void updateStateByAuctionUuid(String auctionUuid, AuctionStateEnum state);

    SearchAuctionPostTitleAndInfluencerNameResponseVo getAuctionPostTitleAndInfluencerName(SearchAuctionPostTitleDto searchAuctionPostTitleDto);
}
