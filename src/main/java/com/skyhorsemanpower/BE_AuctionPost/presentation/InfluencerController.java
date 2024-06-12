package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAddRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAddRequestVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerDetailResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/influencer")
public class InfluencerController {

	private final InfluencerService influencerService;

	@PostMapping("/add")
	public SuccessResponse<Object> addInfluencer(
		@RequestBody InfluencerAddRequestVo influencerAddRequestVo) {
		influencerService.addInfluencer(InfluencerAddRequestDto.voToDto(influencerAddRequestVo));
		return new SuccessResponse<>(null);
	}

	@GetMapping
	public SuccessResponse<InfluencerDetailResponseVo> findInfluencer(
		@RequestParam("influencerId") String influencerUuid) {
		return new SuccessResponse<>(
			InfluencerDetailResponseDto.dtoToVo(influencerService.findInfluencer(influencerUuid)));
	}
}
