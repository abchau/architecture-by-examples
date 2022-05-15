package com.abchau.archexamples.hddd.subscribe.domain.input;

import com.abchau.archexamples.hddd.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hddd.subscribe.domain.exception.EmailAlreadyExistException;
import com.abchau.archexamples.hddd.subscribe.domain.exception.EmailFormatException;

public interface SubscriptionService {

	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailAlreadyExistException;

	public Subscription save(Subscription subscription) throws EmailFormatException;

}