package com.skyhorsemanpower.BE_AuctionPost.presentation;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyhorsemanpower.BE_AuctionPost.GenerateRandom;
import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummariesRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummaryDto;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.InfluencerRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(InfluencerController.class)
class InfluencerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfluencerService influencerService;

    @MockBean
    private InfluencerRepository influencerRepository;

    @Test
    @DisplayName("인플루언서 uuid리스트로 조회 요청시 인플루언서 정보가 담긴 리스트를 반환한다.")
    void findInfluencersTest() throws Exception {
        List<String> influencerUuids = List.of("influencer1", "influencer2", "influencer3");

        List<InfluencerSummaryDto> influencerSummaryDtos = List.of(
            InfluencerSummaryDto.builder()
                .name("장원영")
                .profileImage("https://jwy.png")
                .birthDate(LocalDate.of(2004, 8, 31))
                .description("공주")
                .build(),
            InfluencerSummaryDto.builder()
                .name("카리나")
                .profileImage("https://karina.png")
                .birthDate(LocalDate.of(2000, 4, 11))
                .description("여신")
                .build(),
            InfluencerSummaryDto.builder()
                .name("설윤")
                .profileImage("https://sy.png")
                .birthDate(LocalDate.of(2004, 1, 26))
                .description("요정")
                .build()
        );

        Mockito.when(
            influencerService.getInfluencerSummaries(InfluencerSummariesRequestDto.builder()
                .influencerUuids(influencerUuids)
                .build())).thenReturn(influencerSummaryDtos);

        String request = "?influencerUuids=influencer1,influencer2,influencer3";

        MvcResult result = mockMvc.perform(get("/api/v1/influencer/summarise" + request)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<InfluencerSummaryDto> influencerSummaries = objectMapper.readValue(responseContent,
            new TypeReference<List<InfluencerSummaryDto>>() {
            });

        for (int i = 0; i < influencerSummaries.size(); i++) {
            InfluencerSummaryDto actual = influencerSummaries.get(i);
            InfluencerSummaryDto expected = influencerSummaryDtos.get(i);

            assertThat(actual.getName()).isEqualTo(expected.getName());
            assertThat(actual.getProfileImage()).isEqualTo(expected.getProfileImage());
            assertThat(actual.getBirthDate()).isEqualTo(expected.getBirthDate());
            assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        }
    }

    @Test
    @DisplayName("인플루언서 uuid리스트로 조회 요청시 인플루언서 정보가 담긴 리스트를 반환한다.")
    void findAllInfluencersTest() throws Exception {
        List<InfluencerDetailResponseDto> influencerDetailResponseDtos = List.of(
            InfluencerDetailResponseDto.builder()
                .influencerUuid(GenerateRandom.influencerUuid())
                .name("아이유")
                .profileImage("https://iu.png")
                .birth(LocalDate.of(1993, 5, 16))
                .description("국힙원탑")
                .build(),
            InfluencerDetailResponseDto.builder()
                .influencerUuid(GenerateRandom.influencerUuid())
                .name("장원영")
                .profileImage("https://jwy.png")
                .birth(LocalDate.of(2004, 8, 31))
                .description("공주님")
                .build()
        );

        Mockito.when(influencerService.getAllInfluencers()).thenReturn(influencerDetailResponseDtos);

        MvcResult result = mockMvc.perform(get("/api/v1/influencer/all" )
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();

        assertTrue(responseContent.contains("\"influencerUuid\""));
        assertTrue(responseContent.contains("\"name\""));
        assertTrue(responseContent.contains("\"profileImage\""));
        assertTrue(responseContent.contains("\"birth\""));
        assertTrue(responseContent.contains("\"description\""));
    }
}