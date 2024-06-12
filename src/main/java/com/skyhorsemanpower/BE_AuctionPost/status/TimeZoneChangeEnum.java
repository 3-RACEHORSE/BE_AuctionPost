package com.skyhorsemanpower.BE_AuctionPost.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeZoneChangeEnum {
    UTC_TO_KOREA(9),
    KOREA_TO_UTC(-9);
    private int timeDiff;

    TimeZoneChangeEnum(int timeDiff) {
        this.timeDiff = timeDiff;
    }
}
