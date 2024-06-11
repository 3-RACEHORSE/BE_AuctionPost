package com.skyhorsemanpower.BE_AuctionPost.repository.querydsl;

import java.util.List;

public interface AuctionImagesRepositoryCustom {
    String getThumbnailUrl(String auctionUuid);
    List<String> getImagesUrl(String auctionUuid);
}
