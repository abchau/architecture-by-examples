package com.abchau.archexamples.subscribe.domain.input;

import java.util.Optional;

import com.abchau.archexamples.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.subscribe.domain.exception.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.exception.EmailFormatException;

public interface SubscriptionService {

	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailAlreadyExistException;

	public Optional<Subscription> save(Subscription subscription) throws EmailFormatException;

}