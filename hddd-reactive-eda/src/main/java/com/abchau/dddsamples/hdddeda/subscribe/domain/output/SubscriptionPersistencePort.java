package com.abchau.archexamples.hdddeda.subscribe.domain.output;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;

import reactor.core.publisher.Mono;

public interface SubscriptionPersistencePort {

	public Mono<Subscription> save(final Subscription subscription);

}
