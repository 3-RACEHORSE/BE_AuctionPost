package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.CreateAuctionPostRequestVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAllAuctionResponseVo;
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
    public SuccessResponse<SearchAllAuctionResponseVo> searchAllAuctionPost (
            @RequestHeader(required = false) String uuid,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String influencerName,
            @RequestParam(required = false) AuctionStateEnum auctionState,
            @RequestParam(required = false) String localName,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return new SuccessResponse<>(auctionPostService.searchAllAuction(SearchAllAuctionDto.builder()
                        .uuid(uuid).title(title).influencerName(influencerName).auctionState(auctionState)
                        .localName(localName).page(page).size(size).build()));
    }
}
