package com.skyhorsemanpower.BE_AuctionPost.config.kafkaconfig;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.UpdateTotalDonationUpdateVo;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
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

	@KafkaListener(
		topics = "auction-post-topic",
		groupId = "${spring.kafka.consumer.group-id}"
	)
	public void updateDonationAuctionPost(
		@Payload LinkedHashMap<String, Object> message,
		@Headers MessageHeaders headers
	) {
		log.info("consumer: success >>> message: {}, headers: {}", message.toString(), headers);

		UpdateTotalDonationUpdateVo updateTotalDonationUpdateVo = UpdateTotalDonationUpdateVo.builder()
			.auctionUuid(message.get("auctionUuid").toString())
			.totalDonationAmount(BigDecimal.valueOf((Integer) message.get("totalDonationAmount")))
			.build();

		log.info("consumer: success >>> updateTotalDonationUpdateVo: {}",
			updateTotalDonationUpdateVo.toString());

		auctionPostService.updateTotalDonationAmount(updateTotalDonationUpdateVo);
	}
}
