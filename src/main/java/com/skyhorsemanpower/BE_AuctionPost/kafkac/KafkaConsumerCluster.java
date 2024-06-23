package com.skyhorsemanpower.BE_AuctionPost.kafkac;

import com.skyhorsemanpower.BE_AuctionPost.application.AuctionPostService;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.SearchForChatRoomVo;
import com.skyhorsemanpower.BE_AuctionPost.data.vo.UpdateTotalDonationUpdateVo;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
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

	@KafkaListener(topics = "send-to-auction-for-create-chatroom-topic")
	public void searchInformationForChat(@Payload LinkedHashMap<String, Object> message,
		@Headers MessageHeaders messageHeaders) {
		log.info("consumer: success >>> message: {}, headers: {}", message.toString(),
			messageHeaders);
		//message를 PaymentReadyVo로 변환
		SearchForChatRoomVo searchForChatRoomVo = SearchForChatRoomVo.builder()
			.auctionUuid(message.get("auctionUuid").toString())
			.memberUuids((List<String>) message.get("memberUuids"))
			.build();
		log.info("auctionUuid : {}", searchForChatRoomVo.getAuctionUuid());
		log.info("memberUuids : {}", searchForChatRoomVo.getMemberUuids());
		auctionPostService.searchTitleAndThumbnail(searchForChatRoomVo);
	}
}
