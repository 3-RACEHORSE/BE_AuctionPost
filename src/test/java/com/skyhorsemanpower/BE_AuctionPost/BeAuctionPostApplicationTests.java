//package com.skyhorsemanpower.BE_AuctionPost;
//
//import com.skyhorsemanpower.BE_AuctionPost.data.dto.CreateAuctionPostDto;
//import com.skyhorsemanpower.BE_AuctionPost.domain.AuctionImages;
//import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
//import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
//import com.skyhorsemanpower.BE_AuctionPost.repository.AuctionImagesRepository;
//import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
//import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
//import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.EnumSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class BeAuctionPostApplicationTests {
//
//    @Autowired
//    private CommandAuctionPostRepository commandAuctionPostRepository;
//    @Autowired
//    private ReadAuctionPostRepository readAuctionPostRepository;
//    @Autowired
//    private AuctionImagesRepository auctionImagesRepository;
//
//    @ParameterizedTest
//    @EnumSource(value = AuctionStateEnum.class)
//    public void testCreateAuctionPost(AuctionStateEnum auctionStateEnum) {
//        String[] localNames = {"서울", "부산", "인천", "광주", "대구", "울산", "대전", "경기도", "강원도", "제주도"};
//        String imageUrl = "https://i.namu.wiki/i/RwHbCZcaQyGS1uhvzyE32FUzUw4HjND_72tkSqdLXsf71ywntxEENtg_kHrjkJPoyx30sFbpy4gx8Rgo7kJMXg.webp";
//        for(int j=0; j<10; j++) {
//            CreateAuctionPostDto createAuctionPostDto = CreateAuctionPostDto.builder()
//                .adminUuid("8f687b14-45cf-4151-9706-4168cff862a1")
//                .influencerUuid("influencerUuid")
//                .influencerName("IU")
//                .title("광안리 해변가에서 펩시 어때요?")
//                .content("내용")
//                .numberOfEventParticipants(10)
//                .localName(localNames[j])
//                .eventPlace("이벤트 돔")
//                .eventStartTime(LocalDateTime.now())
//                .eventCloseTime(LocalDateTime.now())
//                .auctionStartTime(LocalDateTime.now().plusDays(5))
//                .startPrice(BigDecimal.valueOf(10000))
//                .incrementUnit(BigDecimal.valueOf(10000))
//                .images(List.of(imageUrl))
//                .thumbnail(imageUrl)
//                .auctionUuid(GenerateRandom.auctionUuid())
//                .build();
//
//            commandAuctionPostRepository.save(CommandAuctionPost.builder()
//                .auctionUuid(createAuctionPostDto.getAuctionUuid())
//                .adminUuid(createAuctionPostDto.getAdminUuid())
//                .influencerUuid(createAuctionPostDto.getInfluencerUuid())
//                .influencerName(createAuctionPostDto.getInfluencerName())
//                .title(createAuctionPostDto.getTitle())
//                .content(createAuctionPostDto.getContent())
//                .numberOfEventParticipants(createAuctionPostDto.getNumberOfEventParticipants())
//                .localName(createAuctionPostDto.getLocalName())
//                .eventPlace(createAuctionPostDto.getEventPlace())
//                .eventStartTime(createAuctionPostDto.getEventStartTime())
//                .eventCloseTime(createAuctionPostDto.getEventCloseTime())
//                .auctionStartTime(createAuctionPostDto.getAuctionStartTime())
//                .startPrice(createAuctionPostDto.getStartPrice())
//                .incrementUnit(createAuctionPostDto.getIncrementUnit())
//                .state(auctionStateEnum)
//                .build());
//
//            auctionImagesRepository.save(
//                AuctionImages.builder()
//                    .auctionUuid(createAuctionPostDto.getAuctionUuid())
//                    .imageUrl(createAuctionPostDto.getThumbnail())
//                    .isThumbnail(true)
//                    .build());
//
//            List<String> images = createAuctionPostDto.getImages();
//
//            for (String image : images) {
//                auctionImagesRepository.save(
//                    AuctionImages.builder()
//                        .auctionUuid(createAuctionPostDto.getAuctionUuid())
//                        .imageUrl(image)
//                        .isThumbnail(false)
//                        .build()
//                );
//            }
//
//            readAuctionPostRepository.save(
//                ReadAuctionPost.builder()
//                    .auctionUuid(createAuctionPostDto.getAuctionUuid())
//                    .adminUuid(createAuctionPostDto.getAdminUuid())
//                    .influencerUuid(createAuctionPostDto.getInfluencerUuid())
//                    .influencerName(createAuctionPostDto.getInfluencerName())
//                    .title(createAuctionPostDto.getTitle())
//                    .content(createAuctionPostDto.getContent())
//                    .numberOfEventParticipants(createAuctionPostDto.getNumberOfEventParticipants())
//                    .localName(createAuctionPostDto.getLocalName())
//                    .eventPlace(createAuctionPostDto.getEventPlace())
//                    .eventStartTime(createAuctionPostDto.getEventStartTime())
//                    .eventCloseTime(createAuctionPostDto.getEventCloseTime())
//                    .auctionStartTime(createAuctionPostDto.getAuctionStartTime())
//                    .startPrice(createAuctionPostDto.getStartPrice())
//                    .incrementUnit(createAuctionPostDto.getIncrementUnit())
//                    .state(auctionStateEnum)
//                    .build()
//            );
//        }
//    }
//}
