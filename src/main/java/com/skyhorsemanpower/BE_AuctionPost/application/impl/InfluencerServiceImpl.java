package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.common.CustomException;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAddRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSearchResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummariesRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummaryDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerUpdateRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.Influencer;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.InfluencerRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import java.util.ArrayList;
import java.util.List;
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

	private final InfluencerRepository influencerRepository;

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
			.birthDate(influencerAddRequestDto.getBirth())
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
	@Transactional
	public void updateInfluencer(InfluencerUpdateRequestDto influencerUpdateRequestDto) {
		Influencer influencer = influencerRepository.findByUuid(
				influencerUpdateRequestDto.getInfluencerUuid())
			.orElseThrow(() -> new CustomException(ResponseStatus.NON_EXISTENT_INFLUENCER));

		influencerRepository.save(Influencer.builder()
			.id(influencer.getId())
			.uuid(influencer.getUuid())
			.name(influencerUpdateRequestDto.getName())
			.phoneNum(influencerUpdateRequestDto.getPhoneNum())
			.profileImage(influencerUpdateRequestDto.getProfileImage())
			.description(influencerUpdateRequestDto.getDescription())
			.build()
		);
	}

	@Override
	@Transactional
	public void removeInfluencer(String influencerUuid) {
		Influencer influencer = influencerRepository.findByUuid(influencerUuid)
			.orElseThrow(() -> new CustomException(ResponseStatus.NON_EXISTENT_INFLUENCER));

		influencerRepository.delete(influencer);
	}

	@Override
	public List<InfluencerSearchResponseDto> searchInfluencer(String name) {
		List<Influencer> influencers = influencerRepository.findByNameContaining(name);
		if (influencers == null) {
			throw new CustomException(ResponseStatus.NON_EXISTENT_INFLUENCER);
		}
		return influencers.stream()
			.map(influencer -> InfluencerSearchResponseDto.builder()
				.influencerUuid(influencer.getUuid())
				.name(influencer.getName())
				.profileImage(influencer.getProfileImage())
				.build())
			.toList();
	}

	@Override
	public List<InfluencerSummaryDto> getInfluencerSummaries(
		InfluencerSummariesRequestDto influencerSummariesRequestDto) {

		List<Influencer> influencers = influencerRepository.findByUuidIn(influencerSummariesRequestDto.getInfluencerUuids());

		if (influencers.isEmpty()) {
			return List.of();
		}

		return influencers.stream().map(influencer ->
				InfluencerSummaryDto.builder()
					.name(influencer.getName())
					.profileImage(influencer.getProfileImage())
					.birthDate(influencer.getBirthDate())
					.description(influencer.getDescription())
					.build()
			).toList();
	}
}
