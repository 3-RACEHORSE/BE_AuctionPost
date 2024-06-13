package com.skyhorsemanpower.BE_AuctionPost.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public enum AuctionLimitTimeEnum {
    BANK_CHECK(LocalTime.of(22, 0));

    private LocalTime time;

    AuctionLimitTimeEnum(LocalTime time) {
        this.time = time;
    }
}
