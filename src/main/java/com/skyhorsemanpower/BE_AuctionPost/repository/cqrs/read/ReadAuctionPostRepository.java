package com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.CustomReadAuctionPostRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadAuctionPostRepository extends MongoRepository<ReadAuctionPost, String>, CustomReadAuctionPostRepository {
    Optional<ReadAuctionPost> findByAuctionUuid(String auctionUuid);
}
