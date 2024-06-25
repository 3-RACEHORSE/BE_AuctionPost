package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchAuctionForChatRoomVo {

    private String title;
    private String thumbnail;
    private String auctionUuid;
    private List<String> memberUuids;
}
