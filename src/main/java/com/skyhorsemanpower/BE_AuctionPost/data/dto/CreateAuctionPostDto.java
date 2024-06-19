package com.skyhorsemanpower.BE_AuctionPost.data.dto;

import com.skyhorsemanpower.BE_AuctionPost.common.DateTimeConverter;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.CreateAuctionPostRequestVo;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAuctionPostDto {

    private String adminUuid;
    private String influencerUuid;
    private String influencerName;
    private String title;
    private String content;
    private int numberOfEventParticipants;
    private String localName;
    private String eventPlace;
    private long eventStartTime;
    private long eventCloseTime;
    private long auctionStartTime;
    private BigDecimal startPrice;
    private BigDecimal incrementUnit;
    private String thumbnail;
    private List<String> images;
    private String auctionUuid;

    @Builder
    public CreateAuctionPostDto(String adminUuid, String influencerUuid, String influencerName,
        String title,
        String content, int numberOfEventParticipants, String localName, String eventPlace,
        long eventStartTime, long eventCloseTime,
        long auctionStartTime, BigDecimal startPrice,
        BigDecimal incrementUnit, String thumbnail, List<String> images, String auctionUuid) {
        this.adminUuid = adminUuid;
        this.influencerUuid = influencerUuid;
        this.influencerName = influencerName;
        this.title = title;
        this.content = content;
        this.numberOfEventParticipants = numberOfEventParticipants;
        this.localName = localName;
        this.eventPlace = eventPlace;
        this.eventStartTime = eventStartTime;
        this.eventCloseTime = eventCloseTime;
        this.auctionStartTime = auctionStartTime;
        this.startPrice = startPrice;
        this.incrementUnit = incrementUnit;
        this.thumbnail = thumbnail;
        this.images = images;
        this.auctionUuid = auctionUuid;
    }


    public static CreateAuctionPostDto voToDto(String uuid,
        CreateAuctionPostRequestVo createAuctionPostRequestVo) {
        return CreateAuctionPostDto.builder()
            .adminUuid(uuid)
            .influencerUuid(createAuctionPostRequestVo.getInfluencerUuid())
            .influencerName(createAuctionPostRequestVo.getInfluencerName())
            .title(createAuctionPostRequestVo.getTitle())
            .content(createAuctionPostRequestVo.getContent())
            .numberOfEventParticipants(createAuctionPostRequestVo.getNumberOfEventParticipants())
            .localName(createAuctionPostRequestVo.getLocalName())
            .eventPlace(createAuctionPostRequestVo.getEventPlace())
            .eventStartTime(
                DateTimeConverter.localDateTimeToInstant(
                    createAuctionPostRequestVo.getEventStartTime())
            )
            .eventCloseTime(
                DateTimeConverter.localDateTimeToInstant(
                    createAuctionPostRequestVo.getEventCloseTime())
            )
            .auctionStartTime(
                DateTimeConverter.localDateTimeToInstant(
                    createAuctionPostRequestVo.getAuctionStartTime())
            )
            .startPrice(createAuctionPostRequestVo.getStartPrice())
            .incrementUnit(createAuctionPostRequestVo.getIncrementUnit())
            .thumbnail(createAuctionPostRequestVo.getThumbnail())
            .images(createAuctionPostRequestVo.getImages())
            .build();
    }
}
