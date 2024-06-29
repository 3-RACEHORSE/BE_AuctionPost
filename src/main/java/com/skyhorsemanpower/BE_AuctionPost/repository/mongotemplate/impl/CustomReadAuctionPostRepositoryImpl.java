package com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.impl;

import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAuctionPostTitleDto;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchAuctionPostTitleAndInfluencerNameResponseVo;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.CustomReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionPostFilteringEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

        // 입력값에 따른 경매글 리스트 조회
        if (searchAllAuctionDto.getSearchContent() != null) {
            criteria.orOperator(
                    Criteria.where("title").regex(searchAllAuctionDto.getSearchContent(), "i"),
                    Criteria.where("influencerName").regex(searchAllAuctionDto.getSearchContent(), "i")
            );
            hasCriteria = true;
        }

        // 지역명과 경매 상태에 따른 경매글 리스트 조회
        else {
            if (searchAllAuctionDto.getAuctionState() != null) {

                // 모든 경매글 검색
                if (searchAllAuctionDto.getAuctionState().equals(AuctionPostFilteringEnum.ALL_AUCTION)) {
                    // ne는 not equal 이라는 의미
                    criteria.and("state").ne(null);
                    hasCriteria = true;
                }

                // 특정 상태 경매글 검색
                else {
                    criteria.and("state").is(searchAllAuctionDto.getAuctionState());
                    hasCriteria = true;
                }
            }
            // 지역명 필터링
            if (searchAllAuctionDto.getLocalName() != null) {
                criteria.and("localName").regex(searchAllAuctionDto.getLocalName(), "i");
                hasCriteria = true;
            }
        }

        log.info("findAllAuctionPost's hasCriteria >>> {}", hasCriteria);

        // criteria가 비어있는 경우
        if(!hasCriteria) {
            log.info("criteria is blank");
            throw new CustomException(ResponseStatus.BLANK_CRITERIA);
        }

        Query query = new Query(criteria).with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .with(Sort.by(Sort.Order.desc("auctionStartTime"))); // auctionStartTime 내림차순 정렬

        log.info("Qeury >>> {}", query);

        List<ReadAuctionPost> auctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        return PageableExecutionUtils.getPage(
                auctionPosts,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), ReadAuctionPost.class)
        );
    }

    @Override
    public Page<ReadAuctionPost> findAllInfluencerAuctionPost(
            InfluencerAllAuctionPostDto influencerAllAuctionPostDto, Pageable pageable) {
        log.info("SearchAllAuctionDto >>> {}", influencerAllAuctionPostDto.toString());

        Criteria criteria = new Criteria();
        boolean hasCriteria = false;

        if (influencerAllAuctionPostDto.getInfluencerUuid() != null) {
            criteria.and("influencerUuid").is(influencerAllAuctionPostDto.getInfluencerUuid());
            hasCriteria = true;
        }

        if (influencerAllAuctionPostDto.getAuctionState() != null) {
            criteria.and("state").is(influencerAllAuctionPostDto.getAuctionState());
            hasCriteria = true;
        }

        log.info("findAllInfluencerAuctionPost's hasCriteria >>> {}", hasCriteria);

        // criteria가 비어있는 경우
        if(!hasCriteria) {
            log.info("criteria is blank");
            throw new CustomException(ResponseStatus.NO_DATA);
        }

        Query query = new Query(criteria).with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .with(Sort.by(Sort.Order.desc("auctionStartTime"))); // auctionStartTime 내림차순 정렬

        log.info("Qeury >>> {}", query);

        List<ReadAuctionPost> auctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        return PageableExecutionUtils.getPage(
                auctionPosts,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), ReadAuctionPost.class)
        );    }

    @Override
    public void updateStateByAuctionUuid(String auctionUuid, AuctionStateEnum state) {
        Query query = new Query(Criteria.where("auctionUuid").is(auctionUuid));
        Update update = new Update().set("state", state);
        mongoTemplate.updateFirst(query, update, ReadAuctionPost.class);
    }

    @Override
    public SearchAuctionPostTitleAndInfluencerNameResponseVo getAuctionPostTitleAndInfluencerName(
            SearchAuctionPostTitleDto searchAuctionPostTitleDto) {
        log.info("SearchAuctionPostTitleDto >>> {}", searchAuctionPostTitleDto.toString());

        String searchData = searchAuctionPostTitleDto.getData();
        List<String> result = new ArrayList<>();
        Criteria criteria = new Criteria();

        // 인플루언서 검색
        criteria.and("influencerName").regex(searchData, "i");

        Query query = new Query(criteria);

        // 필요한 필드만 선택
        query.fields().include("influencerName");

        // 1개 제한
        query.limit(1);

        log.info("Qeury >>> {}", query);

        List<ReadAuctionPost> readAuctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        for(ReadAuctionPost readAuctionPost : readAuctionPosts) {
            log.info("readAuctionPost >>> {}", readAuctionPost.toString());
            result.add(readAuctionPost.getInfluencerName());
        }

        // 경매 제목 검색
        criteria = new Criteria();
        criteria.and("title").regex(searchData, "i");

        query = new Query(criteria);

        query.fields().include("title");

        // 9개 제한
        query.limit(9);

        log.info("Qeury >>> {}", query);

        readAuctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        for(ReadAuctionPost readAuctionPost : readAuctionPosts) {
            log.info("readAuctionPost >>> {}", readAuctionPost.toString());
            result.add(readAuctionPost.getTitle());
        }

        return SearchAuctionPostTitleAndInfluencerNameResponseVo.builder()
                .result(result).build();
    }

    @Override
    public Page<ReadAuctionPost> findByState(String state, Pageable pageable) {

        Criteria criteria = new Criteria();
        criteria.and("state").is(state);

        Sort sort = Sort.by(Sort.Order.desc("createdAt"));

        Query query = new Query(criteria).with(pageable)
            .limit(pageable.getPageSize()).with(sort);

        List<ReadAuctionPost> auctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        return PageableExecutionUtils.getPage(
            auctionPosts,
            pageable,
            () -> mongoTemplate.count(query.skip(-1).limit(-1), ReadAuctionPost.class)
        );
    }
}
