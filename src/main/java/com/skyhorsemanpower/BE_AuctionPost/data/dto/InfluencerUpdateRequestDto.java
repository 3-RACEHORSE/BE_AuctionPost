package com.skyhorsemanpower.BE_AuctionPost.data.dto;

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
public class InfluencerUpdateRequestDto {

	private String influencerUuid;
	private String name;
	private String phoneNum;
	private String profileImage;
	private String description;
}
