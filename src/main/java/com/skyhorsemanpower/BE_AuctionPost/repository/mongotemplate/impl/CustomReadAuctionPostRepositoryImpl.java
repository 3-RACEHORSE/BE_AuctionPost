package com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.impl;

import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.CustomReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomReadAuctionPostRepositoryImpl implements CustomReadAuctionPostRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ReadAuctionPost> findAllAuctionPost(SearchAllAuctionPostDto searchAllAuctionDto, Pageable pageable) {
        log.info("SearchAllAuctionDto >>> {}", searchAllAuctionDto.toString());

        Criteria criteria = new Criteria();
        boolean hasCriteria = false;

        if (searchAllAuctionDto.getAuctionState() != null) {
            criteria.and("state").is(searchAllAuctionDto.getAuctionState());
            hasCriteria = true;
        }
        if (searchAllAuctionDto.getTitle() != null) {
            criteria.and("title").regex(searchAllAuctionDto.getTitle(), "i");
            hasCriteria = true;
        }
        if (searchAllAuctionDto.getLocalName() != null) {
            criteria.and("localName").regex(searchAllAuctionDto.getLocalName(), "i");
            hasCriteria = true;
        }
        if (searchAllAuctionDto.getInfluencerName() != null) {
            criteria.and("influencerName").regex(searchAllAuctionDto.getInfluencerName(), "i");
            hasCriteria = true;
        }

        log.info("hasCriteria >>> {}", hasCriteria);

        // criteria가 비어있는 경우
        if(!hasCriteria) {
            log.info("criteria is blank");
            throw new CustomException(ResponseStatus.NO_DATA);
        }

        Query query = new Query(criteria).with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());

        log.info("Qeury >>> {}", query);

        List<ReadAuctionPost> auctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        return PageableExecutionUtils.getPage(
                auctionPosts,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), ReadAuctionPost.class)
        );
    }
}
