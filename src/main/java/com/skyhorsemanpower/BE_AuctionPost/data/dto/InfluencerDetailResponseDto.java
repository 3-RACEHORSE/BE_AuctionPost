package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerDetailResponseVo;
import java.time.LocalDate;
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
public class InfluencerDetailResponseDto {

	private String influencerUuid;
	private String name;
	private String profileImage;
	private LocalDate birth;
	private String description;

	public static InfluencerDetailResponseVo dtoToVo(
		InfluencerDetailResponseDto influencerDetailResponseDto) {
		return new InfluencerDetailResponseVo(
			influencerDetailResponseDto.getInfluencerUuid(),
			influencerDetailResponseDto.getName(),
			influencerDetailResponseDto.getProfileImage(),
			influencerDetailResponseDto.getBirth(),
			influencerDetailResponseDto.getDescription()
		);
	}
}
