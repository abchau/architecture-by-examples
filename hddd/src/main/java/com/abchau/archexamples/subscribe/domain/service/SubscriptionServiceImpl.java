package com.abchau.archexamples.subscribe.domain.service;

import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.subscribe.domain.exception.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.exception.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.exception.EmailPersistenceErrorException;
import com.abchau.archexamples.subscribe.domain.input.SubscriptionService;
import com.abchau.archexamples.subscribe.domain.output.SubscriptionPersistencePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionPersistencePort subscriptionPersistencePort;

	@Autowired
	public SubscriptionServiceImpl(final SubscriptionPersistencePort subscriptionPersistencePort) {
		this.subscriptionPersistencePort = subscriptionPersistencePort;
	}

	@Override
	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailAlreadyExistException {
		log.trace(() -> "isAlreadyExist()...invoked");
		Objects.requireNonNull(emailAddress, "email.empty");
		
		int count = subscriptionPersistencePort.countByEmail(emailAddress);

		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

	@Override
	public Optional<Subscription> save(Subscription subscription) throws EmailFormatException {
		log.trace(() -> "save()...invoked");
		Objects.requireNonNull(subscription, "email.empty");
		Objects.requireNonNull(subscription.getEmailAddress(), "email.empty");

		// (1) domain validation
		if (subscription.getEmailAddress().getValue() == null || "".equalsIgnoreCase(subscription.getEmailAddress().getValue())) {
			throw new NullPointerException("email.empty");
		}

		// (2) domain validation & throw domain error
		if (!subscription.getEmailAddress().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}
		
		try {
			return Optional.of(subscriptionPersistencePort.save(subscription));
		} catch (Exception e) {
			log.error("", e);
			return Optional.empty();
		} 
	}

}
