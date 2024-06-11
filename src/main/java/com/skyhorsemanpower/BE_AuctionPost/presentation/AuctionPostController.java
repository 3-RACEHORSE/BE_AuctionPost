package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.*;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "경매글 서비스", description = "경매글 서비스 API")
@RequestMapping("/api/v1/auction-post")
public class AuctionPostController {
    private final AuctionPostService auctionPostService;
    private final ReadAuctionPostRepository readAuctionPostRepository;

    // 경매글 등록
    @PostMapping("")
    @Operation(summary = "경매글 등록", description = "경매글 등록")
    public SuccessResponse<Object> createAuctionPost (
            @RequestHeader String uuid,
            @RequestBody CreateAuctionPostRequestVo createAuctionPostRequestVo) {
        auctionPostService.createAuctionPost(CreateAuctionPostDto.voToDto(uuid, createAuctionPostRequestVo));
        return new SuccessResponse<>(null);
    }

    // 경매글 리스트 조회
    @GetMapping("/search")
    @Operation(summary = "경매 리스트 조회", description = "경매 리스트 조회")
    public SuccessResponse<SearchAllAuctionPostResponseVo> searchAllAuctionPost (
            @RequestHeader(required = false) String uuid,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String influencerName,
            @RequestParam(required = false) AuctionStateEnum auctionState,
            @RequestParam(required = false) String localName,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return new SuccessResponse<>(auctionPostService.searchAllAuction(SearchAllAuctionPostDto.builder()
                        .uuid(uuid).title(title).influencerName(influencerName).auctionState(auctionState)
                        .localName(localName).page(page).size(size).build()));
    }

    // 경매글 상세조회
    @GetMapping("/{auctionUuid}")
    @Operation(summary = "경매 상세 조회", description = "경매 상세 조회")
    public SuccessResponse<SearchAuctionResponseVo> searchAuctionPost (
            @RequestHeader String uuid,
            @PathVariable("auctionUuid") String auctionUuid) {
        return new SuccessResponse<>(auctionPostService.searchAuctionPost(SearchAuctionPostDto.builder()
                        .auctionUuid(auctionUuid).build()));
    }

    // 특정 인플루언서가 참여하는 경매글 조회
    @GetMapping("/influencer/{influencerUuid}")
    @Operation(summary = "특정 인플루언서가 참여하는 경매글 리스트 조회", description = "특정 인플루언서가 참여하는 경매글 리스트 조회")
    public SuccessResponse<InfluencerAllAuctionPostResponseVo> influencerAuctionPost (
            @RequestHeader String uuid,
            @PathVariable("influencerUuid") String influencerUuid,
            @RequestParam(required = false) AuctionStateEnum auctionState,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return new SuccessResponse<>(auctionPostService.influencerAuctionPost(InfluencerAllAuctionPostDto.builder()
                        .influencerUuid(influencerUuid)
                .auctionState(auctionState).page(page).size(size).build()));
    }

    // 경매글 상태를 변경
    @PutMapping("change-state")
    @Operation(summary = "특정 경매글 상태를 변경", description = "특정 경매글 상태를 변경")
    public SuccessResponse<Object> changeAuctionPostState (
            @RequestHeader String uuid,
            @RequestBody ChangeAuctionPostStateRequestVo changeAuctionPostStateRequestVo) {

        // 현재는 mongoDB 데이터만 수정
        readAuctionPostRepository.updateStateByAuctionUuid(
                changeAuctionPostStateRequestVo.getAuctionUuid(),
                changeAuctionPostStateRequestVo.getState());
        return new SuccessResponse<>(null);
    }
}
