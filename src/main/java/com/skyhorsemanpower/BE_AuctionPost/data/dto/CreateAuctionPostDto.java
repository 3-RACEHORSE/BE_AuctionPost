package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.vo.CreateAuctionPostRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateAuctionPostDto {
    private String sellerUuid;
    private String title;
    private String content;
    private String category;
    private BigDecimal minimumPrice;
    private String thumbnail;
    private List<String> images;
    private String auctionUuid;

    @Builder
    public CreateAuctionPostDto(String sellerUuid, String title, String content, String category,
                                BigDecimal minimumPrice, String thumbnail, List<String> images) {
        this.sellerUuid = sellerUuid;
        this.title = title;
        this.content = content;
        this.category = category;
        this.minimumPrice = minimumPrice;
        this.thumbnail = thumbnail;
        this.images = images;
    }

    public static CreateAuctionPostDto voToDto(String uuid, CreateAuctionPostRequestVo createAuctionPostRequestVo) {
        return CreateAuctionPostDto.builder()
                .sellerUuid(uuid)
                .title(createAuctionPostRequestVo.getTitle())
                .content(createAuctionPostRequestVo.getContent())
                .category(createAuctionPostRequestVo.getCategory())
                .minimumPrice(createAuctionPostRequestVo.getMinimumPrice())
                .thumbnail(createAuctionPostRequestVo.getThumbnail())
                .images(createAuctionPostRequestVo.getImages())
                .build();
    }
}
