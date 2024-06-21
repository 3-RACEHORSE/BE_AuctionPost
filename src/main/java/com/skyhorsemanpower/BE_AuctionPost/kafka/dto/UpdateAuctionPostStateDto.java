package com.skyhorsemanpower.BE_AuctionPost.kafka.dto;

import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UpdateAuctionPostStateDto {
    private String auctionUuid;
    private AuctionStateEnum auctionState;

    @Builder
    public UpdateAuctionPostStateDto(String auctionUuid, AuctionStateEnum auctionState) {
        this.auctionUuid = auctionUuid;
        this.auctionState = auctionState;
    }
}
