package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAddRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSearchResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummariesRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummaryDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerAddRequestVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerDetailResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerSearchResponseVo;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "인플루언서 추가", description = "인플루언서 정보를 추가합니다.")
    public SuccessResponse<Object> addInfluencer(
        @RequestBody InfluencerAddRequestVo influencerAddRequestVo) {
        influencerService.addInfluencer(InfluencerAddRequestDto.voToDto(influencerAddRequestVo));
        return new SuccessResponse<>(null);
    }

    @GetMapping
    @Operation(summary = "인플루언서 조회", description = "인플루언서 정보를 조회합니다.")
    public SuccessResponse<InfluencerDetailResponseVo> findInfluencer(
        @RequestParam("influencerUuid") String influencerUuid) {
        return new SuccessResponse<>(
            InfluencerDetailResponseDto.dtoToVo(influencerService.findInfluencer(influencerUuid)));
    }

    @GetMapping("/search")
    @Operation(summary = "인플루언서 검색", description = "인플루언서 이름으로 검색하여 리스트를 반환합니다.")
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
    @Operation(summary = "인플루언서 삭제", description = "인플루언서 정보를 삭제합니다.")
    public SuccessResponse<Object> deleteInfluencer(
        @RequestParam("influencerId") String influencerUuid) {
        influencerService.removeInfluencer(influencerUuid);
        return new SuccessResponse<>(null);
    }

    @GetMapping("/summarise")
    @Operation(summary = "인플루언서 리스트 조회", description = "influencerUuid 리스트를 받아 해당 인플루언서들의 이름, 프로필 사진을 리스트에 담아 반환합니다.")
    public SuccessResponse<List<InfluencerSummaryDto>> findInfluencers(
        @RequestParam String influencerUuids
    ) {
        return new SuccessResponse<>(influencerService.getInfluencerSummaries(
            InfluencerSummariesRequestDto.queryParamToDto(influencerUuids)));
    }
}
