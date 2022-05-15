package com.abchau.archexamples.hddd.subscribe.domain.output;

import com.abchau.archexamples.hddd.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;

public interface SubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

    Subscription save(Subscription subscriptionPersistence);

}

