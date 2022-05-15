package com.abchau.archexamples.hex.subscribe.application;

import java.util.Objects;

import com.abchau.archexamples.hex.subscribe.application.core.EmailAddress;
import com.abchau.archexamples.hex.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.hex.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.hex.subscribe.application.core.Subscription;
import com.abchau.archexamples.hex.subscribe.application.core.SubscriptionFacade;
import com.abchau.archexamples.hex.subscribe.application.core.SubscriptionPersistencePort;
import com.abchau.archexamples.hex.subscribe.application.core.usecase.CreateSubscriptionUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionFacadeImpl implements SubscriptionFacade {

	// (1) one class per use case
	private CreateSubscriptionUseCase createSubscriptionUseCase;

	@Autowired
	public SubscriptionFacadeImpl(CreateSubscriptionUseCase createSubscriptionUseCase) {
		this.createSubscriptionUseCase = createSubscriptionUseCase;
	}

	@Transactional
	@Override
	public Subscription createSubscription(String email) throws EmailFormatException, EmailAlreadyExistException, Exception {
		log.trace(() -> "createSubscription()...invoked");
		Objects.requireNonNull(email);

		// (3) factory pattern
		Subscription subscription = Subscription.of(email);
		log.debug(() -> "subscription: " + subscription);

		return createSubscriptionUseCase.execute(subscription);
	}

}
