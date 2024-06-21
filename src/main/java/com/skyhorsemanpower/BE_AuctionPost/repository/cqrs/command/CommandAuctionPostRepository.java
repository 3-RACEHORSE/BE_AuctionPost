package com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandAuctionPostRepository extends JpaRepository<CommandAuctionPost, Long> {
    Optional<CommandAuctionPost> findByAuctionUuid(String auctionUuid);
}
