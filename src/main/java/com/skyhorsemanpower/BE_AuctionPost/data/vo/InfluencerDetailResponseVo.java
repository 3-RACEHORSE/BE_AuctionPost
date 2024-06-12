package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class InfluencerDetailResponseVo {

	private String influencerUuid;
	private String name;
	private String profileImage;
	private LocalDate birth;
	private String description;

	public InfluencerDetailResponseVo(String influencerUuid, String name, String profileImage,
		LocalDate birth,
		String description) {
		this.influencerUuid = influencerUuid;
		this.name = name;
		this.profileImage = profileImage;
		this.birth = birth;
		this.description = description;
	}
}
