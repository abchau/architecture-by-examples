package com.abchau.archexamples.ddd.subscribe.domain.service.impl;

import java.util.Objects;

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
		Objects.requireNonNull(emailAddress);
		
		int count = subscriptionRepository.countByEmail(emailAddress);

		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

	@Override
	public Subscription save(Subscription subscription) throws EmailFormatException {
		log.trace(() -> "save()...invoked");
		Objects.requireNonNull(subscription);

		// (2) throw domain error
		if (!subscription.getEmailAddress().isValidFormat()) {
			throw new EmailFormatException("email.format");
		}

		return subscriptionRepository.save(subscription);
	}

}
