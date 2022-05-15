package com.abchau.archexamples.ddd.subscribe.application.impl;


import java.util.Objects;

import com.abchau.archexamples.ddd.subscribe.application.SubscriptionServiceFacade;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.ddd.subscribe.domain.service.SubscriptionService;
import com.abchau.archexamples.ddd.subscribe.application.dto.SubscriptionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionServiceFacadeImpl implements SubscriptionServiceFacade {

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscriptionServiceFacadeImpl(final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	// (1) tasks coordination and wrap tasks in a transaction
	@Transactional
	@Override
	// (2) better method name
	public SubscriptionDto createSubscription(CreateSubscriptionCommand createSubscriptionCommand) throws IllegalArgumentException, Exception {
		log.trace(() -> "createSubscription()...invoked");
		Objects.requireNonNull(createSubscriptionCommand);

		String email = createSubscriptionCommand.getEmail();
		log.debug(() -> "email: " + email);

		// (3) also do validation here
		if (email == null || "".equalsIgnoreCase(email)) {
			throw new IllegalArgumentException("email.empty");
		}

		// (3) demonstrate translating domain exception
		try {
			EmailAddress emailAddress = EmailAddress.of(email);
			subscriptionService.isAlreadyExist(emailAddress);

			// (4) factory pattern
			Subscription subscription = Subscription.of(emailAddress);
			log.debug(() -> "subscription: " + subscription);
			
			Subscription savedSubscription = subscriptionService.save(subscription);
			log.debug(() -> "savedSubscription: " + savedSubscription);
			
			// (5) demonstrate ACL
			SubscriptionDto savedSubscriptionDto = AntiCorruptionLayer.translate(savedSubscription);
			log.debug(() -> "savedSubscriptionDto: " + savedSubscriptionDto);
			
			return savedSubscriptionDto;
		} catch (EmailAlreadyExistException e) {
			throw new IllegalArgumentException("email.duplicate");
		}  catch (EmailFormatException e) {
			throw new IllegalArgumentException("email.format");
		} catch (Exception e) {
			throw new Exception("error.unknown");
		}
	}

	// (5) Anti-corruption layer (ACL)
	private static class AntiCorruptionLayer {

		public static SubscriptionDto translate(Subscription subscription) {
			Objects.requireNonNull(subscription);
	
			SubscriptionDto subscriptionDto = new SubscriptionDto();
			subscriptionDto.setId(subscriptionDto.getId());
			subscriptionDto.setEmail(subscription.getEmailAddress().getValue());
			subscriptionDto.setStatus(subscription.getStatus());
			subscriptionDto.setCreatedAt(subscription.getCreatedAt());
			subscriptionDto.setLastUpdatedAt(subscription.getLastUpdatedAt());

			return subscriptionDto;
		}
	}

}
