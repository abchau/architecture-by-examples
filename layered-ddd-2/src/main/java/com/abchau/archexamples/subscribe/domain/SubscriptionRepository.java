package com.abchau.archexamples.subscribe.domain;

import java.util.List;

// Domain Service
public interface SubscriptionRepository {

    public int countByEmail(EmailAddress emailAddress);

    public Subscription save(Subscription subscription);

}
