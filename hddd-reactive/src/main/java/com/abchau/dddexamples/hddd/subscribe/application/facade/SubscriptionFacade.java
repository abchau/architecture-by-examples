package com.abchau.archexamples.hddd.subscribe.application.facade;

import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;

import reactor.core.publisher.Mono;

/**
 * Domain API to Subscription Domain
 * 
 * @common-name Service
 * @dddarch-name Domain Service
 * @hexarch-name Inpurt Port/Primary Port/Driving Port
 */
public interface SubscriptionFacade {

	public Mono<Subscription> subscribe(final SubscriptionCommand subscriptionCommand);

}
