package com.skyhorsemanpower.BE_AuctionPost.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class EventStartTimeDto {

    private String auctionUuid;
    private long eventStartTime;

    @Builder
    public EventStartTimeDto(String auctionUuid, long eventStartTime) {
        this.auctionUuid = auctionUuid;
        this.eventStartTime = eventStartTime;
    }
}
