package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InfluencerSummaryDto {
    private String name;
    private String profileImage;
    private LocalDate birthDate;
    private String description;
}
