package com.abchau.archexamples.subscribe.application;

import java.util.List;

import com.abchau.archexamples.subscribe.domain.Subscription;

// Application Service
public interface SubscriptionFacade  {

	public Subscription createSubscription(CreateSubscriptionCommand createSubscriptionCommand);

	public static record CreateSubscriptionCommand(String email) {
		public static CreateSubscriptionCommand of(String email) {
			return new CreateSubscriptionCommand(email);
		}
	}

}
