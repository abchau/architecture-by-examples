package com.abchau.archexamples.subscribe.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

import com.abchau.archexamples.subscribe.domain.Subscription;

@SecondaryPort
public interface SaveSubscriptionPort {

	Subscription save(Subscription subscriptionPersistence);

}

