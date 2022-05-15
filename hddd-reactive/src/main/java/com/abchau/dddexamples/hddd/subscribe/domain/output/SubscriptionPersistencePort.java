package com.abchau.archexamples.hddd.subscribe.domain.output;

import com.abchau.archexamples.hddd.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;

import reactor.core.publisher.Mono;

public interface SubscriptionPersistencePort {

	public Mono<Subscription> findByEmailAddress(final EmailAddress emailAddress);
	
	public Mono<Subscription> save(final Subscription subscription);

}
