package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.MainPagePostResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainPagePostResponseVo {

	private String auctionUuid;
	private String title;
	private long eventStartTime;
	private AuctionStateEnum state;
	private String thumbnail;

	@Builder
	public MainPagePostResponseVo(String auctionUuid, String title, long eventStartTime, AuctionStateEnum state, String thumbnail) {
		this.auctionUuid = auctionUuid;
		this.title = title;
		this.eventStartTime = eventStartTime;
		this.state = state;
		this.thumbnail = thumbnail;
	}
}
