package com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomReadAuctionPostRepository {
    Page<ReadAuctionPost> findAllAuctionPost(SearchAllAuctionDto searchAllAuctionDto, Pageable pageable);
}
