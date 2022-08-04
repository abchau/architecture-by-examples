package com.abchau.archexamples.subscribe.application.usecase;

import java.util.Objects;

import org.jmolecules.architecture.hexagonal.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abchau.archexamples.subscribe.domain.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.application.inputport.CreateSubscriptionUseCasePort;
import com.abchau.archexamples.subscribe.application.outputport.CreateSubscriptionPersistencePort;
import com.abchau.archexamples.subscribe.application.outputport.QuerySubscriptionPersistencePort;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class CreateSubscriptionUseCase implements CreateSubscriptionUseCasePort {

	private CreateSubscriptionPersistencePort createSubscriptionPersistencePort;

	private QuerySubscriptionPersistencePort querySubscriptionPersistencePort;

	@Autowired
	public CreateSubscriptionUseCase(
		final CreateSubscriptionPersistencePort createSubscriptionPersistencePort,
		final QuerySubscriptionPersistencePort querySubscriptionPersistencePort
	) {
		this.createSubscriptionPersistencePort = createSubscriptionPersistencePort;
		this.querySubscriptionPersistencePort = querySubscriptionPersistencePort;
	}

	@Transactional
	public Subscription execute(Subscription subscription) throws EmailFormatException, EmailAlreadyExistException, Exception {
		log.trace("execute()...invoked");
		Objects.requireNonNull(subscription);

		// (1) must do domain validation before executing domain logic
		int count = querySubscriptionPersistencePort.countByEmail(subscription.getEmailAddress());
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		// (2) only execute after validation
		subscription.toConfirmed();
		return createSubscriptionPersistencePort.save(subscription);
	}

}

