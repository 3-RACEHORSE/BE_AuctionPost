package com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadAuctionPostRepository extends MongoRepository<ReadAuctionPost, String> {
}
