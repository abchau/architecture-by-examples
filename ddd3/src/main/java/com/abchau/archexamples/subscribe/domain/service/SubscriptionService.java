package com.abchau.archexamples.subscribe.domain.service;

import com.abchau.archexamples.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.subscribe.domain.model.subscription.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.model.subscription.Subscription;

public interface SubscriptionService {

	public boolean isEmailAlreadyExist(EmailAddress emailAddress) throws EmailIsEmptyException, EmailAlreadyExistException;

	public Subscription createSubscription(Subscription subscription) throws EmailIsEmptyException, EmailFormatException, EmailAlreadyExistException, InvalidSubscriptionStatusException, CannotCreateSubscriptionException;

}
