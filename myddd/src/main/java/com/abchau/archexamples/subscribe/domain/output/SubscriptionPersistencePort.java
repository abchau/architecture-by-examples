package com.abchau.archexamples.subscribe.domain.output;

import com.abchau.archexamples.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.subscribe.domain.entity.Subscription;

public interface SubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

    Subscription save(Subscription subscriptionPersistence);

}

