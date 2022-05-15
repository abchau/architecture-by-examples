package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

import java.util.Optional;

public interface SubscriptionRepository {

    int countByEmail(EmailAddress emailAddress);

    Optional<Subscription> save(Subscription subscription);

}
