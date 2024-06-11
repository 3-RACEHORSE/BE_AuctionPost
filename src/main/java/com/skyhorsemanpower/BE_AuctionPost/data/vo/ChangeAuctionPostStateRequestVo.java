package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChangeAuctionPostStateRequestVo {
    private String auctionUuid;
    private AuctionStateEnum state;
}
