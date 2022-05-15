package com.abchau.archexamples.hdddeda.subscribe.application.messaging;

import java.util.HashMap;
import java.util.Map;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hdddeda.subscribe.domain.event.SubscriptionCreatedEvent;
import com.abchau.archexamples.hdddeda.subscribe.domain.event.SubscriptionDeclinedEvent;
import com.abchau.archexamples.hdddeda.subscribe.domain.event.SubscriptionRequestedEvent;
import com.abchau.archexamples.hdddeda.subscribe.domain.input.SubscriptionService;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Primary
@Service
final class SpringKafkaSubscriptionEventSubscriber /*implements SubscriptionEventInputPort*/ {

	// private ReactiveKafkaConsumerTemplate<String, Object>
	// reactiveKafkaConsumerTemplate;

	private SubscriptionService subscriptionService;

	@Autowired
	public SpringKafkaSubscriptionEventSubscriber(
			final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@KafkaListener(topics = "#{'historia.Subscription.event.kafka.subscription-topic'}")
	public void consume(final SubscriptionRequestedEvent event) {
		log.trace(() -> "consume(requested)...invoked");

		final Subscription subscription = event.getData();

		subscriptionService.createSubscription(subscription);
	}

	@KafkaListener(topics = "#{'historia.Subscription.event.kafka.subscription-topic'}")
	public void consume(final SubscriptionDeclinedEvent event) {
		log.trace(() -> "consume(declined)...invoked");

		final Subscription subscription = event.getData();

		subscriptionService.declineSubscription(subscription);
	}

}
