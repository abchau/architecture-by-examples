package com.abchau.archexamples.subscribe.application;

import com.abchau.archexamples.subscribe.application.core.Subscription;

public interface SubscriptionFacade {

	public Subscription createSubscription(String email) throws IllegalArgumentException, Exception;

}
