package com.abchau.archexamples.subscribe.application;

import java.util.Objects;

import com.abchau.archexamples.subscribe.application.core.EmailAddress;
import com.abchau.archexamples.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.subscribe.application.core.Subscription;
import com.abchau.archexamples.subscribe.application.core.usecase.CreateSubscriptionUseCase;

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
	public Subscription createSubscription(String email) throws IllegalArgumentException, Exception {
		log.trace(() -> "createSubscription()...invoked");
		Objects.requireNonNull(email);

		Subscription subscription = Subscription.of(EmailAddress.of(email));
		log.debug(() -> "subscription: " + subscription);

		try {
			return createSubscriptionUseCase.execute(subscription);
		} catch (EmailFormatException | EmailAlreadyExistException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

}
