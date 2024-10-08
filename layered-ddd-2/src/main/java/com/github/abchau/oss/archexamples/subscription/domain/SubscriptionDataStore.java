package com.github.abchau.oss.archexamples.subscription.domain;

import java.util.List;

public interface SubscriptionDataStore {

	public List<Subscription> findAll();

    public int countByEmail(EmailAddress emailAddress);

    public Subscription save(Subscription subscription);

}
