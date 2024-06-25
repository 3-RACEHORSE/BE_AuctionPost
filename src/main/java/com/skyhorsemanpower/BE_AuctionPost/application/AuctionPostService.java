package com.skyhorsemanpower.BE_AuctionPost.application;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.MainPagePostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAllAuctionPostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAuctionResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchForChatRoomVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.AuctionTotalDonationVo;
import com.skyhorsemanpower.BE_AuctionPost.kafka.dto.EventStartTimeDto;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.util.List;

public interface AuctionPostService {

	void createAuctionPost(CreateAuctionPostDto createAuctionPostDto);

	SearchAllAuctionPostResponseVo searchAllAuction(
		SearchAllAuctionPostDto searchAllAuctionPostDto);

	SearchAuctionResponseVo searchAuctionPost(SearchAuctionPostDto searchAuctionPostDto);

	InfluencerAllAuctionPostResponseVo influencerAuctionPost(
		InfluencerAllAuctionPostDto influencerAllAuctionPostDto);

	void updateTotalDonationAmount(AuctionTotalDonationVo auctionTotalDonationVo);

	List<MainPagePostResponseVo> mainPagePost();

	void searchTitleAndThumbnail(SearchForChatRoomVo searchForChatRoomVo);
	Boolean auctionState(String auctionUuid);

	EventStartTimeDto updateStateByAuctionUuid(String auctionUuid, AuctionStateEnum state);

}
