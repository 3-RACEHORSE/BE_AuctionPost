package com.skyhorsemanpower.BE_AuctionPost.repository;

import com.skyhorsemanpower.BE_AuctionPost.domain.AuctionImages;
import com.skyhorsemanpower.BE_AuctionPost.repository.querydsl.AuctionImagesRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionImagesRepository extends JpaRepository<AuctionImages, Long>, AuctionImagesRepositoryCustom {
//    AuctionImages findByAuctionUuid(String auctionUuid);
}
