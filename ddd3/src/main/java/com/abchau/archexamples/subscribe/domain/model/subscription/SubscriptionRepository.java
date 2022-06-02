package com.abchau.archexamples.subscribe.domain.model.subscription;

public interface SubscriptionRepository {

    int countByEmail(EmailAddress emailAddress);

    Subscription save(Subscription subscription);

}
