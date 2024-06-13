package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAddRequestVo;
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
public class InfluencerAddRequestDto {

	private String name;
	private String profileImage;
	private String phoneNum;
	private LocalDate birth;
	private String description;

	public static InfluencerAddRequestDto voToDto(
		InfluencerAddRequestVo influencerAddRequestVo) {
		return InfluencerAddRequestDto.builder()
			.name(influencerAddRequestVo.getName())
			.profileImage(influencerAddRequestVo.getProfileImage())
			.phoneNum(influencerAddRequestVo.getPhoneNum())
			.birth(influencerAddRequestVo.getBirth())
			.description(influencerAddRequestVo.getDescription())
			.build();
	}
}
