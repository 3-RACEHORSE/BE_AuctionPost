package com.skyhorsemanpower.BE_AuctionPost.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuctionEndTimeState {
    TWO_HOUR(2);

    private int endTime;

    AuctionEndTimeState(int endTime) {
        this.endTime = endTime;
    }
}
