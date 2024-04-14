package com.github.abchau.oss.archexamples.subscription.application;

import java.util.List;

import com.github.abchau.oss.archexamples.subscription.domain.Subscription;

public interface SubscriptionFacade  {

	public List<Subscription> showSubscription();

	public Subscription createSubscription(CreateSubscriptionCommand createSubscriptionCommand);

	public static record CreateSubscriptionCommand(String email) {
	public static CreateSubscriptionCommand of(String email) {
			return new CreateSubscriptionCommand(email);
		}
	}

}
