package com.abchau.archexamples.subscribe.application.core.inputport;

import com.abchau.archexamples.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.subscribe.application.core.Subscription;

public interface CreateSubscriptionUseCasePort {

	public Subscription execute(Subscription subscription) throws EmailFormatException, EmailAlreadyExistException, Exception;

}
