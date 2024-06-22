package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.vo.MainPagePostResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPagePostResponseDto {

	private String auctionUuid;
	private String title;
	private long eventStartTime;
	private AuctionStateEnum state;
	private String thumbnail;

}
