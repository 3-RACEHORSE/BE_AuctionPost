package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InfluencerSummariesRequestDto {

    private List<String> influencerUuids;

    public static InfluencerSummariesRequestDto queryParamToDto(String influencerUuidsQueryParam) {
        List<String> influencerUuids = Arrays.asList(influencerUuidsQueryParam.split(","));
        return InfluencerSummariesRequestDto.builder()
            .influencerUuids(influencerUuids)
            .build();
    }
}
