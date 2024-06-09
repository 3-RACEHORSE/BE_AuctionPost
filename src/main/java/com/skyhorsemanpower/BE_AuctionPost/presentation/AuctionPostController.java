package com.skyhorsemanpower.BE_AuctionPost.presentation;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.common.SuccessResponse;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.CreateAuctionPostRequestVo;
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
}
