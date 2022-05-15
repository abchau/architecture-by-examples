package com.abchau.archexamples.hdddeda.subscribe.spring.config;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

// @Configuration
public class ReactiveKafkaProducerConfig {

    // @Bean
    public ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate(final KafkaProperties kafkaProperties) {
        final Map<String, Object> properties = kafkaProperties.buildProducerProperties();

        return new ReactiveKafkaProducerTemplate<String, Object>(SenderOptions.create(properties));
    }

}
