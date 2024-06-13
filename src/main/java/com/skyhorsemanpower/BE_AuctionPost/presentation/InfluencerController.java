package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAddRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSearchResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAddRequestVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerDetailResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerSearchResponseVo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@GetMapping("/search")
	public SuccessResponse<List<InfluencerSearchResponseVo>> searchInfluencer(
		@RequestParam("name") String name) {
		List<InfluencerSearchResponseDto> influencerSearchResponseDtos = influencerService.searchInfluencer(
			name);
		List<InfluencerSearchResponseVo> influencers = influencerSearchResponseDtos.stream()
			.map(dto -> new InfluencerSearchResponseVo(dto.getInfluencerUuid(), dto.getName(),
				dto.getProfileImage()))
			.collect(Collectors.toList());

		return new SuccessResponse<>(influencers);
	}

	@DeleteMapping("/delete")
	public SuccessResponse<Object> deleteInfluencer(
		@RequestParam("influencerId") String influencerUuid) {
		influencerService.removeInfluencer(influencerUuid);
		return new SuccessResponse<>(null);
	}
}
