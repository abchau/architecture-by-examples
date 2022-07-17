package com.abchau.archexamples.subscribe.application.core.usecase;

import java.util.Objects;

import com.abchau.archexamples.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.subscribe.application.core.Subscription;
import com.abchau.archexamples.subscribe.application.inputport.CreateSubscriptionUseCasePort;
import com.abchau.archexamples.subscribe.application.outputport.CreateSubscriptionPersistencePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CreateSubscriptionUseCase implements CreateSubscriptionUseCasePort {

	private CreateSubscriptionPersistencePort createSubscriptionPersistencePort;

	@Autowired
	public CreateSubscriptionUseCase(final CreateSubscriptionPersistencePort createSubscriptionPersistencePort) {
		this.createSubscriptionPersistencePort = createSubscriptionPersistencePort;
	}

	@Transactional
	public Subscription execute(Subscription subscription) throws EmailFormatException, EmailAlreadyExistException, Exception {
		log.trace("execute()...invoked");
		Objects.requireNonNull(subscription);

		// (1) must do domain validation before executing domain logic
		int count = createSubscriptionPersistencePort.countByEmail(subscription.getEmailAddress());
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		// (2) only execute after validation
		subscription.toConfirmed();
		return createSubscriptionPersistencePort.save(subscription);
	}

}

