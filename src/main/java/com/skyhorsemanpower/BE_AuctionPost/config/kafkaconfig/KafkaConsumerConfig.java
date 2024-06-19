package com.skyhorsemanpower.BE_AuctionPost.config.kafkaconfig;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value('${spring.kafka.bootstrap-servers}')
	private String bootstrapServers;

	@Value('${spring.kafka.consumer.group-id}')
	private String groupId;

	@Bean
	public ConsumerFactory<String, Object> pushEntityConsumerFactory() {
		JsonDeserializer<Object> deserialize = gcmPushEntityDeserializer();
		return new DefaultKafkaConsumerFactory<>(
			consumerFactoryConfig(deserialize),
			new StringDeserializer(),
			deserialize);
	}

	private Map<String, Object> consumerFactoryConfig(JsonDeserializer<Object> deserialize) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserialize);
		return props;
	}

	private JsonDeserializer<Object> gcmPushEntityDeserializer() {
		JsonDeserializer<Object> deserialize = new JsonDeserializer<>(Object.class);
		deserialize.setRemoveTypeHeaders(false);
		deserialize.addTrustedPackages("*");
		deserialize.setUseTypeMapperForKey(true);
		return deserialize;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object>
	kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory =
			new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(pushEntityConsumerFactory());
		return factory;
	}
}
