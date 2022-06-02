package com.abchau.archexamples.subscribe.domain.service.impl;

import com.abchau.archexamples.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.subscribe.domain.model.subscription.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.subscribe.domain.model.subscription.SubscriptionRepository;
import com.abchau.archexamples.subscribe.domain.service.SubscriptionService;

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
	public boolean isEmailAlreadyExist(EmailAddress emailAddress) throws EmailIsEmptyException, EmailAlreadyExistException {
		log.trace(() -> "isEmailAlreadyExist()...invoked");
		log.debug(() -> "emailAddress: " + emailAddress);

		// (1) must do input validation before executing domain logic
		if (emailAddress == null 
			|| (emailAddress.getValue() == null || "".equalsIgnoreCase(emailAddress.getValue()))
		) {
			throw new EmailIsEmptyException("email.empty");
		}

		// (2) only execute after validation
		int count = subscriptionRepository.countByEmail(emailAddress);
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

	@Override
	public Subscription createSubscription(Subscription subscription) throws EmailIsEmptyException, EmailFormatException, EmailAlreadyExistException, InvalidSubscriptionStatusException, CannotCreateSubscriptionException {
		log.trace(() -> "createSubscription()...invoked");
		log.debug(() -> "subscription: " + subscription);
		
		// (1) must do input validation before executing domain logic
		if (subscription == null 
			|| subscription.getEmailAddress() == null
			|| (subscription.getEmailAddress().getValue() == null || "".equalsIgnoreCase(subscription.getEmailAddress().getValue()))
		) {
			throw new EmailIsEmptyException("email.empty");
		}

		// (2) must do domain validation before executing domain logic
		if (!subscription.getEmailAddress().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}

		// (2) must do domain validation before executing domain logic
		if (!subscription.isStatusValidated()) {
			throw new InvalidSubscriptionStatusException("error.status.invalid");
		}

		// (2) must do domain validation before executing domain logic
		if (isEmailAlreadyExist(subscription.getEmailAddress())) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		// (5) validate state before changing
		if (subscription.isValidForConfirmed()) {
			subscription.toConfirmed();
		}

		// (2) must do domain validation before executing domain logic
		if (!subscription.isStatusConfirmed()) {
			throw new InvalidSubscriptionStatusException("error.status.invalid");
		}
		
		// (3) only execute after validation
		try {
			return subscriptionRepository.save(subscription);
		}
		// (4) wraps whatever thrown from underlying layer into a domain exception
		catch (Exception e) {
			log.error("couldn't create subscription", e);
			throw new CannotCreateSubscriptionException("error.create");
		} 
	}

}
