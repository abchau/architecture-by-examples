package com.abchau.archexamples.hddd.subscribe.domain.input;

import com.abchau.archexamples.hddd.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;

import reactor.core.publisher.Mono;

/**
 * Domain API to Subscription Domain
 * 
 * @common-name Service
 * @dddarch-name Domain Service
 * @hexarch-name Inpurt Port/Primary Port/Driving Port
 */
public interface SubscriptionService {

	/**
	 * a Subscription is received
	 * @param Subscription
	 * @return
	 */
	public Mono<Subscription> requestSubscription(final Subscription subscription);
	
	/**
	 * create a registartion record
	 * @param Subscription
	 * @return
	 */
	public Mono<Subscription> createSubscription(final Subscription subscription);

	/**
	 * decline a registartion 
	 * @param Subscription
	 * @return
	 */
	public Mono<Subscription> declineSubscription(final Subscription subscription);

}
