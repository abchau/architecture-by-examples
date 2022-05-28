package com.abchau.archexamples.subscribe.application.core;

public interface SubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

    Subscription save(Subscription subscriptionPersistence);

}

