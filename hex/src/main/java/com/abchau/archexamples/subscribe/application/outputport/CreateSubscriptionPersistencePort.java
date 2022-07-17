package com.abchau.archexamples.subscribe.application.outputport;
import com.abchau.archexamples.subscribe.application.core.Subscription;

public interface CreateSubscriptionPersistencePort {

	Subscription save(Subscription subscriptionPersistence);

}

