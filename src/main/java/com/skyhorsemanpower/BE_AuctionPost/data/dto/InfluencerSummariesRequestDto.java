package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.data.vo.InfluencerSummariesRequestVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InfluencerSummariesRequestDto {

    private List<String> influencerUuids;

    public static InfluencerSummariesRequestDto voToDto(InfluencerSummariesRequestVo vo) {
        return InfluencerSummariesRequestDto.builder()
            .influencerUuids(vo.getInfluencerUuids())
            .build();
    }
}
