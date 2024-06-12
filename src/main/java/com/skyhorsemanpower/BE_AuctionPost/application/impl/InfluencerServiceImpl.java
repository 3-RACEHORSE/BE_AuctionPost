package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAddRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerUpdateRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.Influencer;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.InfluencerRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InfluencerServiceImpl implements InfluencerService {

	private InfluencerRepository influencerRepository;

	//uuid생성
	private String createUuid() {
		String character = "0123456789";
		StringBuilder uuid = new StringBuilder("");
		Random random = new Random();
		for (int i = 0; i < 9; i++) {
			uuid.append(character.charAt(random.nextInt(character.length())));
		}
		return uuid.toString();
	}

	@Override
	@Transactional
	public void addInfluencer(InfluencerAddRequestDto influencerAddRequestDto) {
		influencerRepository.findByNameAndPhoneNumAndBirthDate(influencerAddRequestDto.getName(),
				influencerAddRequestDto.getPhoneNum(), influencerAddRequestDto.getBirth())
			.ifPresent(influencer -> {
				throw new CustomException(ResponseStatus.ALREADY_REGISTERED_INFLUENCER);
			});

		String influencerUuid = createUuid();

		Influencer influencer = Influencer.builder()
			.uuid(influencerUuid)
			.name(influencerAddRequestDto.getName())
			.phoneNum(influencerAddRequestDto.getPhoneNum())
			.profileImage(influencerAddRequestDto.getProfileImage())
			.description(influencerAddRequestDto.getDescription())
			.build();

		influencerRepository.save(influencer);
	}

	@Override
	public InfluencerDetailResponseDto findInfluencer(String influencerUuid) {
		Influencer influencer = influencerRepository.findByUuid(influencerUuid)
			.orElseThrow(() -> new CustomException(ResponseStatus.NON_EXISTENT_INFLUENCER));

		return InfluencerDetailResponseDto.builder()
			.influencerUuid(influencer.getUuid())
			.name(influencer.getName())
			.profileImage(influencer.getProfileImage())
			.description(influencer.getDescription())
			.build();
	}

	@Override
	public void updateInfluencer(InfluencerUpdateRequestDto influencerUpdateRequestDto) {

	}

	@Override
	public void removeInfluencer(String influencerUuid) {

	}
}