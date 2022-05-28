package com.abchau.archexamples.threeddd.subscribe.domain.model.subscription;

public interface SubscriptionRepository {

    int countByEmail(EmailAddress emailAddress);

    Subscription save(Subscription subscription);

}
