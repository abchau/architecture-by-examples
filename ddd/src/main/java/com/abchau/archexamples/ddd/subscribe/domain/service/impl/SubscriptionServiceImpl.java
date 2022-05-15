package com.abchau.archexamples.ddd.subscribe.domain.service.impl;

import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.SubscriptionRepository;
import com.abchau.archexamples.ddd.subscribe.domain.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	@Autowired
	public SubscriptionServiceImpl(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	public boolean isAlreadyExist(EmailAddress emailAddress) throws EmailAlreadyExistException {
		log.trace(() -> "countByEmail()...invoked");
		Objects.requireNonNull(emailAddress, "email.empty");

		int count = subscriptionRepository.countByEmail(emailAddress);

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

		return subscriptionRepository.save(subscription);
	}

}
