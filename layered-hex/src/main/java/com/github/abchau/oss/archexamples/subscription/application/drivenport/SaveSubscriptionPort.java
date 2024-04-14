package com.github.abchau.oss.archexamples.subscription.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

import com.github.abchau.oss.archexamples.subscription.application.Subscription;

@SecondaryPort
public interface SaveSubscriptionPort {

	Subscription save(Subscription subscriptionPersistence);

}

