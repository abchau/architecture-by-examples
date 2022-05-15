package com.abchau.archexamples.hddd.subscribe.domain.service;

import java.util.Objects;

import com.abchau.archexamples.hddd.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hddd.subscribe.domain.exception.EmailAlreadyExistException;
import com.abchau.archexamples.hddd.subscribe.domain.exception.EmailFormatException;
import com.abchau.archexamples.hddd.subscribe.domain.input.SubscriptionService;
import com.abchau.archexamples.hddd.subscribe.domain.output.SubscriptionPersistencePort;

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
		Objects.requireNonNull(emailAddress);
		
		int count = subscriptionPersistencePort.countByEmail(emailAddress);

		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

	@Override
	public Subscription save(Subscription subscription) throws EmailFormatException {
		log.trace(() -> "save()...invoked");
		Objects.requireNonNull(subscription);

		// (2) throw domain exception
		if (!subscription.getEmailAddress().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}

		return subscriptionPersistencePort.save(subscription);
	}

}
