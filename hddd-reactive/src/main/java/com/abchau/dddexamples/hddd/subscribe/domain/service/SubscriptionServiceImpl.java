package com.abchau.archexamples.hddd.subscribe.domain.service;

import java.util.Objects;

import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hddd.subscribe.domain.input.SubscriptionService;
import com.abchau.archexamples.hddd.subscribe.domain.output.SubscriptionPersistencePort;

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
 * @hexarch-name Use Cases
 */
@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionPersistencePort subscriptionPersistenceService;

	@Autowired
	public SubscriptionServiceImpl(final SubscriptionPersistencePort subscriptionPersistenceService) {
		this.subscriptionPersistenceService = subscriptionPersistenceService;
	}

	@Override
	public Mono<Subscription> requestSubscription(final Subscription subscription) {
		log.trace(() -> "requestSubscription()...invoked");

		Objects.requireNonNull(subscription);

		return Mono.just(subscription)
			.doOnNext(r -> log.debug(() -> "r: " + r))
			// transit to "requested" state
			.map(Subscription::requested);
	}

	@Override
	public Mono<Subscription> createSubscription(final Subscription subscription) {
		log.trace(() -> "createSubscription()...invoked");

		Objects.requireNonNull(subscription);
		
		return Mono.just(subscription)
			// validate if current state is valid for transiting to "Created" state
			.doOnNext(Subscription::isValidForCreated)
			// transit from "requested" to "Created" state
			.map(Subscription::created)
			// tell persistence service provider to save the changes into database
			.flatMap(subscriptionPersistenceService::save);
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
			.flatMap(subscriptionPersistenceService::save);
	}
	
}
