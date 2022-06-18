package com.abchau.archexamples.subscribe.application.core.usecase;

import java.util.Objects;

import com.abchau.archexamples.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.subscribe.application.core.Subscription;
import com.abchau.archexamples.subscribe.application.core.SubscriptionPersistencePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CreateSubscriptionUseCase {

	private SubscriptionPersistencePort subscriptionPersistencePort;

	@Autowired
	public CreateSubscriptionUseCase(final SubscriptionPersistencePort subscriptionPersistencePort) {
		this.subscriptionPersistencePort = subscriptionPersistencePort;
	}

	public Subscription execute(Subscription subscription) throws EmailFormatException, EmailAlreadyExistException, Exception {
		log.trace("execute()...invoked");
		Objects.requireNonNull(subscription);

		// (1) must do domain validation before executing domain logic
		if (!subscription.getEmailAddress().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}

		// (1) must do domain validation before executing domain logic
		int count = subscriptionPersistencePort.countByEmail(subscription.getEmailAddress());
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		// (2) only execute after validation
		return subscriptionPersistencePort.save(subscription);
	}

}
