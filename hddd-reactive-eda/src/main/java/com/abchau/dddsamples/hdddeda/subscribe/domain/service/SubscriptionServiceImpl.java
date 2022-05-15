package com.abchau.archexamples.hdddeda.subscribe.domain.service;

import java.util.Objects;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hdddeda.subscribe.domain.input.SubscriptionService;
import com.abchau.archexamples.hdddeda.subscribe.domain.output.SubscriptionEventPublisher;
import com.abchau.archexamples.hdddeda.subscribe.domain.output.SubscriptionPersistencePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

/**
 * 
 * @common-layer Service Layer
 * @common-name Service e.g. SubscriptionService
 * @dddarch-layer Application Layer
 * @dddarch-name Domain Service
 * @dddarch-layer Domain Layer
 * @hexarch-name Use Cases e.g. SubscriptionUseCases which contains RequestSubscriptionUseCase, CreateSubscriptionUseCase	, etc
 */
@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionPersistencePort subscriptionPersistenceService;

	private SubscriptionEventPublisher subscriptionEventService;

	@Autowired
	public SubscriptionServiceImpl(
			final SubscriptionPersistencePort subscriptionPersistenceService,
			final SubscriptionEventPublisher subscriptionEventService) {
		this.subscriptionPersistenceService = subscriptionPersistenceService;
		this.subscriptionEventService = subscriptionEventService;
	}

	@Override
	public Mono<Subscription> requestSubscription(final Subscription subscription) {
		log.trace(() -> "requestSubscription()...invoked");

		Objects.requireNonNull(subscription);

		return Mono.just(subscription)
			.doOnNext(r -> log.debug(() -> "r: " + r))
			// transit to "requested" state
			.map(Subscription::requested)
			// tell event service provider to publish a "SubscriptionRequestedEvent" event
			.doOnSuccess(subscriptionEventService::requested);
	}

	@Override
	public Mono<Subscription> createSubscription(final Subscription subscription) {
		log.trace(() -> "createSubscription()...invoked");

		Objects.requireNonNull(subscription);
		
		return Mono.just(subscription)
			// validate if current state is valid for transiting to "Created" state
			.doOnNext(Subscription::isValidForCreated)
			// transit from "requested" to "created" state
			.map(Subscription::created)
			// tell persistence service provider to save the changes into database
			.flatMap(subscriptionPersistenceService::save)
			// tell event service provider to publish a "SubscriptionCreatedEvent" event
			.doOnSuccess(subscriptionEventService::created);
	}

	@Override
	public Mono<Subscription> declineSubscription(final Subscription subscription) {
		log.trace(() -> "declineSubscription()...invoked");

		Objects.requireNonNull(subscription);

		return Mono.just(subscription)
			// validate if current state is valid for transiting to "declined" state
			.doOnNext(Subscription::isValidForDeclined)
			// transit from "Created" to "declined" state
			.map(Subscription::declined)
			// tell persistence service provider to save the changes into database
			.flatMap(subscriptionPersistenceService::save)
			// tell event service provider to publish a "SubscriptionDeclinedEvent" event
			.doOnSuccess(subscriptionEventService::declined);
	}
	
}
