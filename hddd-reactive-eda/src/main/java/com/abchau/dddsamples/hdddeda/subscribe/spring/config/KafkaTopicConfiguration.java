package com.abchau.archexamples.hdddeda.subscribe.spring.config;

import java.util.Map;
import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Use this when adding topic dynamicly in your app
 */
@Configuration
public class KafkaTopicConfiguration {
    
    // @Value(value = "${kafka.bootstrapAddress}")
    // private String bootstrapAddress;

    // @Bean
    // public KafkaAdmin kafkaAdmin() {
    //     Map<String, Object> configs = new HashMap<>();
    //     configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    //     return new KafkaAdmin(configs);
    // }
    
    // @Bean
    // public NewTopic SubscriptionTopic() {
    //     //  return new NewTopic("baeldung", 1, (short) 1);
    //      return new NewTopic("Subscription", 1, (short) 1);
    // }

}