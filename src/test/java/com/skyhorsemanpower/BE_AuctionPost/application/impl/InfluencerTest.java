package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.Influencer;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.InfluencerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InfluencerTest {

	private InfluencerService influencerService;
	private InfluencerRepository influencerRepository = Mockito.mock(InfluencerRepository.class);

	@BeforeEach
	public void setUp() {
		influencerService = new InfluencerServiceImpl(influencerRepository);
	}

	@Test
	@DisplayName("인플루언서 조회 테스트")
	public void findInfluencerTest() {
		// given
		String expectedUuid = "expectedUuid";
		String expectedName = "expectedName";
		String expectedPhoneNum = "expectedPhoneNum";
		String expectedProfileImage = "expectedProfileImage";
		String expectedDescription = "expectedDescription";

		Influencer expectedInfluencer = Influencer.builder()
			.uuid(expectedUuid)
			.name(expectedName)
			.phoneNum(expectedPhoneNum)
			.profileImage(expectedProfileImage)
			.description(expectedDescription)
			.build();
		// when
		InfluencerDetailResponseDto influencerDetailResponseDto = influencerService.findInfluencer(
			expectedUuid);
		// then
		assertEquals(expectedUuid, influencerDetailResponseDto.getInfluencerUuid());
	}
}