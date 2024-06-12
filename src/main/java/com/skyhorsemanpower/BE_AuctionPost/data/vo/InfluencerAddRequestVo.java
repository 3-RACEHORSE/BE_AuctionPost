package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class InfluencerAddRequestVo {

	private String name;
	private String phoneNum;
	private String profileImage;
	private LocalDate birth;
	private String description;
}
