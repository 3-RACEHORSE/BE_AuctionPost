package com.skyhorsemanpower.BE_AuctionPost.kafka;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.AuctionTotalDonationVo;
import com.skyhorsemanpower.BE_AuctionPost.kafka.Topics.Constant;
import com.skyhorsemanpower.BE_AuctionPost.kafka.dto.EventStartTimeDto;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.skyhorsemanpower.BE_AuctionPost.kafka.dto.UpdateAuctionPostStateDto;
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
	private final KafkaProducerCluster producer;

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

		AuctionTotalDonationVo auctionTotalDonationVo = AuctionTotalDonationVo.builder()
			.auctionUuid(message.get("auctionUuid").toString())
			.donation(new BigDecimal(message.get("donation").toString()))
			.build();

		log.info("consumer: success >>> updateTotalDonationUpdateVo: {}",
			auctionTotalDonationVo.toString());

		auctionPostService.updateTotalDonationAmount(auctionTotalDonationVo);
	}

	@KafkaListener(topics = Topics.Constant.AUCTION_CLOSE, groupId = "${spring.kafka.consumer.group-id}")
	public void updateAuctionPostState(@Payload LinkedHashMap<String, Object> message,
									   @Headers MessageHeaders messageHeaders) {

		// 상태를 담고 있는 DTO 생성
		UpdateAuctionPostStateDto updateAuctionPostStateDto = UpdateAuctionPostStateDto.builder()
				.auctionUuid(message.get("auctionUuid").toString())
				.auctionState(AuctionStateEnum.valueOf(message.get("auctionState").toString()))
				.build();

		// 경매글 상태 갱신
		EventStartTimeDto eventStartTimeDto;
		try {
			eventStartTimeDto = auctionPostService.updateStateByAuctionUuid(
				updateAuctionPostStateDto.getAuctionUuid(), updateAuctionPostStateDto.getAuctionState());
		} catch (Exception e) {
			log.error("updateAuctionPostState failed: {}", e.getMessage());
			return;
		}

		producer.sendMessage(Constant.EVENT_START_TOPIC, eventStartTimeDto);
	}
}
