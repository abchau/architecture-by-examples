package com.abchau.archexamples.hdddeda.subscribe.spring.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;

import reactor.kafka.receiver.ReceiverOptions;

// @Configuration
public class ReactiveKafkaConsumerConfig {

	// @Value(value = "${FAKE_CONSUMER_DTO_TOPIC}")
	private String topic;

    // @Bean
    public ReceiverOptions<String, Object> kafkaReceiverOptions(final KafkaProperties kafkaProperties) {
        final ReceiverOptions<String, Object> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        Collection<String> topics = Collections.singletonList(topic);
		
		return basicReceiverOptions.subscription(topics);
    }


}
