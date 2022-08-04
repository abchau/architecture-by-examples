package com.abchau.archexamples.subscribe.application.outputport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

import com.abchau.archexamples.subscribe.domain.Subscription;

@SecondaryPort
public interface CreateSubscriptionPersistencePort {

	Subscription save(Subscription subscriptionPersistence);

}

