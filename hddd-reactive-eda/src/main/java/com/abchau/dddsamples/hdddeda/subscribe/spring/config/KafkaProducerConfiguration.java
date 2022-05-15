package com.abchau.archexamples.hdddeda.subscribe.spring.config;

import java.util.Map;
import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfiguration {

    // @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    // private String bootstrapServers;

    // @Value(value = "${spring.kafka.producer.key-serializer}")
    // private String keySerializer;

    // @Value(value = "${spring.kafka.producer.value-serializer}")
    // private String valueSerializer;
	
	@Bean
	public ProducerFactory<String, Object> producerFactory(final KafkaProperties kafkaProperties) {
		// final Map<String, Object> properties = new HashMap<>();

		// properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		// properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
		// properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

        final Map<String, Object> properties = kafkaProperties.buildProducerProperties();

		return new DefaultKafkaProducerFactory<>(properties);
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(final ProducerFactory<String, Object> producerFactory) {
		// return new KafkaTemplate<>(producerFactory());

		return new KafkaTemplate<>(producerFactory);
	}

	// @Bean
	// public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context) {
	//   // ProducerFactory with Bytes serializer
	//   Map<String, Object> props = new HashMap<>();
	//   props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
	// 	bootstrapServers);
	//   props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	// 	StringSerializer.class);
	//   props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	// 	ByteArraySerializer.class);
	//   DefaultKafkaProducerFactory<Object, Object> bytesPF = 
	// 	new DefaultKafkaProducerFactory<>(props);
	//   context.registerBean(DefaultKafkaProducerFactory.class, "bytesPF", bytesPF);
  
	//   // ProducerFactory with String serializer
	//   props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
	// 	StringSerializer.class);
	//   DefaultKafkaProducerFactory<Object, Object> stringPF = 
	// 	new DefaultKafkaProducerFactory<>(props);
  
	//   Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
	//   map.put(Pattern.compile(".*-bytes"), bytesPF);
	//   map.put(Pattern.compile("reflectoring-.*"), stringPF);
	//   return new RoutingKafkaTemplate(map);
	// }

}
