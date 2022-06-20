package com.abchau.archexamples.subscribe.application;

import java.util.Optional;

public interface SubscriptionServiceFacade  {

	public Optional<SubscriptionDto> createSubscription(CreateSubscriptionCommand createSubscriptionCommand) throws IllegalArgumentException, Exception;

	public static record CreateSubscriptionCommand(String email) {}

}
