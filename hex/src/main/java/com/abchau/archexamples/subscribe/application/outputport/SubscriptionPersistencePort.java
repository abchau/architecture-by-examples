package com.abchau.archexamples.subscribe.application.outputport;

import com.abchau.archexamples.subscribe.application.core.EmailAddress;
import com.abchau.archexamples.subscribe.application.core.Subscription;

public interface SubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

    Subscription save(Subscription subscriptionPersistence);

}
