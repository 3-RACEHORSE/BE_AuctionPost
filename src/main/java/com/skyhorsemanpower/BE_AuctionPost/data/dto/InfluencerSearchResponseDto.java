package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerSearchResponseVo;
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
public class InfluencerSearchResponseDto {

	private String influencerUuid;
	private String name;
	private String profileImage;

	public static InfluencerSearchResponseDto voToDto(
		InfluencerSearchResponseVo influencerSearchResponseVo) {
		return InfluencerSearchResponseDto.builder()
			.influencerUuid(influencerSearchResponseVo.getInfluencerUuid())
			.name(influencerSearchResponseVo.getName())
			.profileImage(influencerSearchResponseVo.getProfileImage())
			.build();
	}
}
