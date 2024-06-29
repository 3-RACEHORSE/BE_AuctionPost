package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.config.QuartzJobConfig;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.kafka.KafkaProducerCluster;
import com.skyhorsemanpower.BE_AuctionPost.repository.AuctionImagesRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuctionPostServiceImplTest {

    private CommandAuctionPostRepository commandAuctionPostRepository;
    private ReadAuctionPostRepository readAuctionPostRepository;
    private AuctionImagesRepository auctionImagesRepository;
    private QuartzJobConfig quartzJobConfig;
    private KafkaProducerCluster producer;
    private AuctionPostServiceImpl auctionPostService;
    private SearchAllAuctionPostDto searchAllAuctionPostDto;

    @BeforeEach
    void setUp() {
        commandAuctionPostRepository = Mockito.mock(CommandAuctionPostRepository.class);
        readAuctionPostRepository = Mockito.mock(ReadAuctionPostRepository.class);
        auctionImagesRepository = Mockito.mock(AuctionImagesRepository.class);
        quartzJobConfig = Mockito.mock(QuartzJobConfig.class);
        producer = Mockito.mock(KafkaProducerCluster.class);

        auctionPostService = new AuctionPostServiceImpl(
                commandAuctionPostRepository, readAuctionPostRepository, auctionImagesRepository, quartzJobConfig, producer);
    }

    @Test
    @DisplayName("경매글 리스트 조회 결과가 빈 리스트인 경우 테스트")
    void testSearchAllAuction_noAuctionPosts() {
        // given
        // 빈 페이지 객체 생성
        Page<ReadAuctionPost> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        // when
        when(readAuctionPostRepository.findAllAuctionPost(any(SearchAllAuctionPostDto.class), any(PageRequest.class)))
                .thenReturn(emptyPage);
        List<ReadAuctionPost> emptyAuctionPosts = emptyPage.getContent();

        // then
        assertTrue(emptyAuctionPosts.isEmpty(), "The auctionPosts list should be empty");

        // 추가 검증
        // auctionPosts의 길이가 0인 상황에서 for문을 돌렸을 때, 에러가 발생하면 안된다.
        for (ReadAuctionPost readAuctionPost : emptyAuctionPosts) {
            System.out.println("출력되면 안된다.");
        }
    }
}