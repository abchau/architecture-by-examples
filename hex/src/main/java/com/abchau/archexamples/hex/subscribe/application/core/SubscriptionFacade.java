package com.abchau.archexamples.hex.subscribe.application.core;

public interface SubscriptionFacade {

	public Subscription createSubscription(String email) throws EmailFormatException, EmailAlreadyExistException, Exception;

}
