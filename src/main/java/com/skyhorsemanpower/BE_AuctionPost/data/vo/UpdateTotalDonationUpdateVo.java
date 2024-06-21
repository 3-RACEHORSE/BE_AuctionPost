package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class UpdateTotalDonationUpdateVo {

	private String auctionUuid;
	private BigDecimal totalDonationAmount;
}
