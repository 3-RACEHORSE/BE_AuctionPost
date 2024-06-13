package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import lombok.Getter;

@Getter
public class InfluencerSearchResponseVo {

	private String influencerUuid;
	private String name;
	private String profileImage;

	public InfluencerSearchResponseVo(String influencerUuid, String name, String profileImage) {
		this.influencerUuid = influencerUuid;
		this.name = name;
		this.profileImage = profileImage;
	}
}
