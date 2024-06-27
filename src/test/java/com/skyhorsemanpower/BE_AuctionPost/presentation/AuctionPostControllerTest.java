package com.skyhorsemanpower.BE_AuctionPost.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.skyhorsemanpower.BE_AuctionPost.GenerateRandom;
import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuctionPostController.class)
class AuctionPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionPostService auctionPostService;

    @MockBean
    private ReadAuctionPostRepository readAuctionPostRepository;

    @Test
    void createAuctionPostKstToUtcTimestampTest() throws Exception {
        List<String> images = List.of(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdhdDOGjJEw6-odM_GtFfTfM8ZYMBT5u2Rkx0GZP6Y5afC8BHRA9fnTChjcSahrUO7aN4&usqp=CAU");

        Mockito.doNothing().when(auctionPostService).createAuctionPost(
            CreateAuctionPostDto.builder()
                .adminUuid(GenerateRandom.memberUuid())
                .influencerUuid("karina")
                .influencerName("카리나")
                .title("카리나랑 카레 먹방 찍기")
                .content("카리나의 카레를 먹을 수 있는 절호의 기회!")
                .numberOfEventParticipants(4)
                .localName("서울특별시")
                .eventPlace("성수동 팝업 스토어")
                .eventStartTime(1718940600000L)
                .eventCloseTime(1718946000000L)
                .auctionStartTime(1718854200000L)
                .startPrice(BigDecimal.valueOf(10000))
                .incrementUnit(BigDecimal.valueOf(1000))
                .thumbnail("https://newsimg.sedaily.com/2023/11/15/29X8V5WK5F_1.jpg")
                .images(images)
                .auctionUuid(GenerateRandom.auctionUuid())
                .build()
        );

        String request = """
                {
                  "influencerUuid": "karina",
                  "influencerName": "카리나",
                  "title": "카리나랑 카레 먹방 찍기",
                  "content": "카리나의 카레를 먹을 수 있는 절호의 기회!",
                  "numberOfEventParticipants": 4,
                  "localName": "서울특별시",
                  "eventPlace": "성수동 팝업 스토어",
                  "eventStartTime": "2024-06-21T12:30:00.000",
                  "eventCloseTime": "2024-06-21T14:00:00.000",
                  "auctionStartTime": "2024-06-20T12:30:00.000",
                  "startPrice": 10000,
                  "incrementUnit": 1000,
                  "thumbnail": "https://newsimg.sedaily.com/2023/11/15/29X8V5WK5F_1.jpg",
                  "images": [
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdhdDOGjJEw6-odM_GtFfTfM8ZYMBT5u2Rkx0GZP6Y5afC8BHRA9fnTChjcSahrUO7aN4&usqp=CAU"
                  ]
                }
            """;

        mockMvc.perform(post("/api/v1/auction-post")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("uuid", GenerateRandom.adminUuid())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request))
            .andExpect(status().isOk());

        ArgumentCaptor<CreateAuctionPostDto> captor = ArgumentCaptor.forClass(
            CreateAuctionPostDto.class);
        Mockito.verify(auctionPostService).createAuctionPost(captor.capture());

        CreateAuctionPostDto capturedDto = captor.getValue();
        assertEquals("karina", capturedDto.getInfluencerUuid());
        assertEquals("카리나", capturedDto.getInfluencerName());
        assertEquals("카리나랑 카레 먹방 찍기", capturedDto.getTitle());
        assertEquals("카리나의 카레를 먹을 수 있는 절호의 기회!", capturedDto.getContent());
        assertEquals(4, capturedDto.getNumberOfEventParticipants());
        assertEquals("서울특별시", capturedDto.getLocalName());
        assertEquals("성수동 팝업 스토어", capturedDto.getEventPlace());
        assertEquals(1718940600000L, capturedDto.getEventStartTime());
        assertEquals(1718946000000L, capturedDto.getEventCloseTime());
        assertEquals(1718854200000L, capturedDto.getAuctionStartTime());
        assertEquals(BigDecimal.valueOf(10000), capturedDto.getStartPrice());
        assertEquals(BigDecimal.valueOf(1000), capturedDto.getIncrementUnit());
        assertEquals("https://newsimg.sedaily.com/2023/11/15/29X8V5WK5F_1.jpg",
            capturedDto.getThumbnail());
        assertEquals(images, capturedDto.getImages());
    }

}