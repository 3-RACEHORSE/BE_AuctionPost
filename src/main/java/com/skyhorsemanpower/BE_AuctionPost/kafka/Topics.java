package com.skyhorsemanpower.BE_AuctionPost.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topics {
    INITIAL_AUCTION(Constant.INITIAL_AUCTION)
    ;

    public static class Constant {
        public static final String INITIAL_AUCTION = "initial-auction-topic";
    }

    private final String topic;
}
