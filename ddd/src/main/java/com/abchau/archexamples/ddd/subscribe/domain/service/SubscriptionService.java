package com.abchau.archexamples.ddd.subscribe.domain.service;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;

public interface SubscriptionService {

	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailIsEmptyException, EmailAlreadyExistException;

	public Subscription save(Subscription subscription) throws EmailIsEmptyException, EmailFormatException, EmailAlreadyExistException, CannotCreateSubscriptionException;

}
