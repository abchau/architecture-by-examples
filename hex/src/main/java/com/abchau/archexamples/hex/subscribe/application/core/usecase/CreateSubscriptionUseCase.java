package com.abchau.archexamples.hex.subscribe.application.core.usecase;

import java.util.Objects;

import com.abchau.archexamples.hex.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.hex.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.hex.subscribe.application.core.Subscription;
import com.abchau.archexamples.hex.subscribe.application.core.SubscriptionPersistencePort;

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
		log.trace(() -> "execute()...invoked");
		Objects.requireNonNull(subscription);

		// (2) throw domain exception
		if (!subscription.getEmail().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}

		int count = subscriptionPersistencePort.countByEmail(subscription.getEmail());
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}


		return subscriptionPersistencePort.save(subscription);
	}

}
