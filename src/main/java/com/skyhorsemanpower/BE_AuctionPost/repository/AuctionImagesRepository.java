package com.skyhorsemanpower.BE_AuctionPost.repository;

import com.skyhorsemanpower.BE_AuctionPost.domain.AuctionImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionImagesRepository extends JpaRepository<AuctionImages, Long> {
}
