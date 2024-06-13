package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.*;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.*;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionPostFilteringEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.LocalNameEnum;
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

    // 인플루언서 이름과 제목을 통한 경매글 제목 리스트 반환
    @GetMapping("/search-title")
    @Operation(summary = "인플루언서 이름과 제목을 통한 경매글 리스트 조회", description = "인플루언서 이름과 제목을 통한 경매글 리스트 조회")
    public SuccessResponse<SearchAuctionPostTitleAndInfluencerNameResponseVo> searchAuctionPostTitleAndInfluencerName (
            @RequestHeader(required = false) String uuid,
            @RequestParam String data) {
        return new SuccessResponse<>(readAuctionPostRepository.getAuctionPostTitleAndInfluencerName(
                SearchAuctionPostTitleDto.builder().data(data).build()));
    }

    // 지역 검색(경매글 상태와 무관)
    @GetMapping("/search/local")
    @Operation(summary = "지역을 통한 경매글 리스트 조회", description = "지역을 통한 경매글 리스트 조회")
    public SuccessResponse<SearchAllAuctionPostResponseVo> searchAllAuctionPostByLocalName (
            @RequestHeader(required = false) String uuid,
            @RequestParam String localName,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return new SuccessResponse<>(auctionPostService.searchAllAuction(SearchAllAuctionPostDto.builder()
                .uuid(uuid).localName(localName).auctionState(AuctionPostFilteringEnum.ALL_AUCTION)
                .page(page).size(size).build()));
    }

    // 경매 상태에 따른 검색
    @GetMapping("/search/state")
    @Operation(summary = "상태를 통한 경매글 리스트 조회", description = "상태를 통한 경매글 리스트 조회")
    public SuccessResponse<SearchAllAuctionPostResponseVo> searchAllAuctionPostByState (
            @RequestHeader(required = false) String uuid,
            @RequestParam(required = false) AuctionPostFilteringEnum state,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return new SuccessResponse<>(auctionPostService.searchAllAuction(SearchAllAuctionPostDto.builder()
                .uuid(uuid).auctionState(state)
                .page(page).size(size).build()));
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
