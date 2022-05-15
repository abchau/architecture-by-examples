package com.abchau.archexamples.hdddeda.subscribe.spring.config;

import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConsumerConfiguration {

    // @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    // private String bootstrapServers;
	
	@Bean
	public ConsumerFactory<String, Object> consumerFactory(final KafkaProperties kafkaProperties) {
		// Map<String, Object> properties = new HashMap<>();

		// properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // // properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		// properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        final Map<String, Object> properties = kafkaProperties.buildConsumerProperties();

		return new DefaultKafkaConsumerFactory<>(properties);
	}

	// A KafkaMessageListenerContainer receives all messages from all topics on a single thread.
	// A ConcurrentMessageListenerContainer assigns these messages to multiple KafkaMessageListenerContainer instances to provide multi-threaded capability.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory(final ConsumerFactory<String, Object> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    // @Bean
    // public ConcurrentKafkaListenerContainerFactory<String, Object> userConcurrentKafkaListenerContainerFactory(final ConsumerFactory<String, Object> consumerFactory) {
    //     final ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    //     factory.setConsumerFactory(consumerFactory);
	// 	    factory.setRecordFilterStrategy(
	// 	      record -> {
	// 			boolean isMatch = false;
	// 			try {
	// 				Method getKind = record.value().getClass().getMethod("getKind");
	// 				if ("USER_CREATED_EVENT".equalsIgnoreCase(getKind.invoke(record).toString())) {
	// 					isMatch = true;
	// 				}
	// 			} catch (NoSuchMethodException e) {
	// 				// TODO Auto-generated catch block
	// 				e.printStackTrace();
	// 			} catch (SecurityException e) {
	// 				// TODO Auto-generated catch block
	// 				e.printStackTrace();
	// 			} catch (IllegalAccessException e) {
	// 				// TODO Auto-generated catch block
	// 				e.printStackTrace();
	// 			} catch (IllegalArgumentException e) {
	// 				// TODO Auto-generated catch block
	// 				e.printStackTrace();
	// 			} catch (InvocationTargetException e) {
	// 				// TODO Auto-generated catch block
	// 				e.printStackTrace();
	// 			}

	// 			return isMatch;
	// 		  });

    //     return factory;
    // }

}
