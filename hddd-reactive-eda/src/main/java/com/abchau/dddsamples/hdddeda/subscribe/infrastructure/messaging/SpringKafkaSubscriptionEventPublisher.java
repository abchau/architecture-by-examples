package com.abchau.archexamples.hdddeda.subscribe.infrastructure.messaging;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hdddeda.subscribe.domain.event.SubscriptionCreatedEvent;
import com.abchau.archexamples.hdddeda.subscribe.domain.event.SubscriptionDeclinedEvent;
import com.abchau.archexamples.hdddeda.subscribe.domain.event.SubscriptionRequestedEvent;
import com.abchau.archexamples.hdddeda.subscribe.domain.output.SubscriptionEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
final class SpringKafkaSubscriptionEventPublisher implements SubscriptionEventPublisher {

	@Value(value = "${historia.Subscription.event.kafka-topic:Subscription}")
	private String SubscriptionTopic;

	@Value(value = "${historia.Subscription.event.kafka-dlq:Subscription-dlq}")
	private String SubscriptionDeadLetterQueue;

	private KafkaTemplate<String, Object> kafkaTemplate;

	private ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate;

	@Autowired
	public SpringKafkaSubscriptionEventPublisher(final KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void requested(Subscription subscription) {
		log.trace(() -> "requested()...invoked");

		final SubscriptionRequestedEvent event = SubscriptionRequestedEvent.of(subscription);

		// kafkaTemplate.send(SubscriptionTopic, event);

		// reactiveKafkaProducerTemplate.send(SubscriptionTopic, event)
		// .doOnSuccess(senderResult -> log.info("sent {} offset : {}", event,
		// senderResult.recordMetadata().offset()))
		// .subscribe();
	}

	@Override
	public void created(Subscription subscription) {
		log.trace(() -> "Created()...invoked");

		final SubscriptionCreatedEvent event = SubscriptionCreatedEvent.of(subscription);

		// kafkaTemplate.send(SubscriptionTopic, event);
	}

	@Override
	public void declined(Subscription subscription) {
		log.trace(() -> "declined()...invoked");

		final SubscriptionDeclinedEvent event = SubscriptionDeclinedEvent.of(subscription);

		// kafkaTemplate.send(SubscriptionTopic, event);
	}

}
