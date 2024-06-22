package com.skyhorsemanpower.BE_AuctionPost.application;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.MainPagePostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAuctionResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.UpdateTotalDonationUpdateVo;
import java.util.List;

public interface AuctionPostService {

	void createAuctionPost(CreateAuctionPostDto createAuctionPostDto);

	SearchAllAuctionPostResponseVo searchAllAuction(
		SearchAllAuctionPostDto searchAllAuctionPostDto);

	SearchAuctionResponseVo searchAuctionPost(SearchAuctionPostDto searchAuctionPostDto);

	InfluencerAllAuctionPostResponseVo influencerAuctionPost(
		InfluencerAllAuctionPostDto influencerAllAuctionPostDto);

	void updateTotalDonationAmount(UpdateTotalDonationUpdateVo updateTotalDonationUpdateVo);

	List<MainPagePostResponseVo> mainPagePost();
}
