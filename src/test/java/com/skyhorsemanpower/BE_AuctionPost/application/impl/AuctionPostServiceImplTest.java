package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.config.QuartzConfig;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.repository.AuctionImagesRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuctionPostServiceImplTest {

    @Mock
    CommandAuctionPostRepository commandAuctionPostRepository;
    @Mock
    ReadAuctionPostRepository readAuctionPostRepository;
    @Mock
    AuctionImagesRepository auctionImagesRepository;
    @Mock
    QuartzConfig quartzConfig;
    @Mock
    AuctionPostService auctionPostService;

    // 모든 Test 코드들 진행 전 수행할 메서드
    @BeforeEach
    public void setUp() {
        auctionPostService = new AuctionPostServiceImpl(
          // 서비스 구현체에 주입되는 의존성 정의
          commandAuctionPostRepository, readAuctionPostRepository, auctionImagesRepository, quartzConfig
        );
    }

}