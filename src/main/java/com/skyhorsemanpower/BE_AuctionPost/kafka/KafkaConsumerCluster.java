package com.skyhorsemanpower.BE_AuctionPost.kafka;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.UpdateTotalDonationUpdateVo;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.skyhorsemanpower.BE_AuctionPost.kafka.dto.UpdateAuctionPostStateDto;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.read.ReadAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerCluster {

	private final AuctionPostService auctionPostService;
	private final ReadAuctionPostRepository readAuctionPostRepository;

	@KafkaListener(
		topics = Topics.Constant.AUCTION_POST_DONATION_UPDATE
	)
	public void updateDonationAuctionPost(
		@Payload LinkedHashMap<String, Object> message,
		@Headers MessageHeaders headers
	) {
		log.info("consumer: success >>> message: {}, headers: {}", message.toString(), headers);

		if (message.get("auctionUuid") == null || message.get("donation") == null) {
			log.error("auctionUuid or donation is null");
			return;
		}

		UpdateTotalDonationUpdateVo updateTotalDonationUpdateVo = UpdateTotalDonationUpdateVo.builder()
			.auctionUuid(message.get("auctionUuid").toString())
			.totalDonationAmount(new BigDecimal(message.get("donation").toString()))
			.build();

		log.info("consumer: success >>> updateTotalDonationUpdateVo: {}",
			updateTotalDonationUpdateVo.toString());

		auctionPostService.updateTotalDonationAmount(updateTotalDonationUpdateVo);
	}

	@KafkaListener(topics = Topics.Constant.AUCTION_CLOSE, groupId = "${spring.kafka.consumer.group-id}")
	public void updateAuctionPostState(@Payload LinkedHashMap<String, Object> message,
									   @Headers MessageHeaders messageHeaders) {
		log.info("consumer: success >>> message: {}, headers: {}", message.toString(),
				messageHeaders);

		// 상태를 담고 있는 DTO 생성
		UpdateAuctionPostStateDto updateAuctionPostStateDto = UpdateAuctionPostStateDto.builder()
				.auctionUuid(message.get("auctionUuid").toString())
				.auctionState(AuctionStateEnum.valueOf(message.get("auctionState").toString()))
				.build();
		log.info("UpdateAuctionPostStateDto >>> {}", updateAuctionPostStateDto.toString());

		// 경매글 상태 갱신
		readAuctionPostRepository.updateStateByAuctionUuid(
				updateAuctionPostStateDto.getAuctionUuid(), updateAuctionPostStateDto.getAuctionState());

		log.info("AuctionPost State update success!");
	}
}
