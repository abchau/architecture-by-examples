package com.abchau.archexamples.ddd.subscribe.domain.service;

import java.util.Optional;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;

public interface SubscriptionService {

	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailAlreadyExistException;

	Optional<Subscription> save(Subscription subscription) throws EmailFormatException;

}
