package com.abchau.archexamples.ddd.subscribe.domain.service.impl;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.SubscriptionRepository;
import com.abchau.archexamples.ddd.subscribe.domain.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public final class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	@Autowired
	public SubscriptionServiceImpl(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailIsEmptyException, EmailAlreadyExistException {
		log.trace(() -> "isAlreadyExist()...invoked");
		log.debug(() -> "emailAddress: " + emailAddress);

		// (1) must do domain validation before executing domain logic
		if (emailAddress == null 
			|| (emailAddress.getValue() == null || "".equalsIgnoreCase(emailAddress.getValue()))
		) {
			throw new EmailIsEmptyException("email.empty");
		}

		// (2) only execute after domain validation
		int count = subscriptionRepository.countByEmail(emailAddress);
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

	@Override
	public Subscription save(Subscription subscription) throws EmailIsEmptyException, EmailFormatException, EmailAlreadyExistException, CannotCreateSubscriptionException {
		log.trace(() -> "save()...invoked");
		log.debug(() -> "subscription: " + subscription);
		
		// (1) must do domain validation before executing domain logic
		if (subscription == null 
			|| subscription.getEmailAddress() == null
			|| (subscription.getEmailAddress().getValue() == null || "".equalsIgnoreCase(subscription.getEmailAddress().getValue()))
		) {
			throw new EmailIsEmptyException("email.empty");
		}

		// (1) must do domain validation before executing domain logic
		if (!subscription.getEmailAddress().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}

		// (1) must do domain validation before executing domain logic
		if (isAlreadyExist(subscription.getEmailAddress())) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		// (1) must do domain validation before executing domain logic
		if (!subscription.isStatusConfirmed()) {
			throw new EmailAlreadyExistException("error.status.invalid");
		}
		
		// (2) only execute after domain validation
		try {
			return subscriptionRepository.save(subscription);
		}
		// (3) wraps whatever thrown from underlying layer into a domain exception
		catch (Exception e) {
			log.error("couldn't create subscription", e);
			throw new CannotCreateSubscriptionException("error.create");
		} 
	}

}
