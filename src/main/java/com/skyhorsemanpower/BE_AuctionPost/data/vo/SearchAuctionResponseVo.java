package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class SearchAuctionResponseVo {
    private ReadAuctionPost readAuctionPost;

    private String thumbnail;
    private List<String> images;

    @Builder
    public SearchAuctionResponseVo(ReadAuctionPost readAuctionPost, String thumbnail, List<String> images) {
        this.readAuctionPost = readAuctionPost;
        this.thumbnail = thumbnail;
        this.images = images;
    }
}
