package com.abchau.archexamples.ddd.subscribe.application.impl;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.ddd.subscribe.application.SubscriptionServiceFacade;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.InvalidSubscriptionStatusException;
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
	public Optional<SubscriptionDto> createSubscription(CreateSubscriptionCommand createSubscriptionCommand) throws IllegalArgumentException, Exception {
		log.trace(() -> "createSubscription()...invoked");
		log.debug(() -> "createSubscriptionCommand: " + createSubscriptionCommand);

		Objects.requireNonNull(createSubscriptionCommand);

		String email = createSubscriptionCommand.getEmail();
		log.debug(() -> "email: " + email);

		// (3) do validation in application layer
		if (email == null || "".equalsIgnoreCase(email)) {
			throw new IllegalArgumentException("email.empty");
		}

		// (3) demonstrate translating domain exception
		try {
			// (4) factory pattern
			Subscription newSubscription = Subscription.of(EmailAddress.of(email), ZonedDateTime.now(Clock.systemDefaultZone()));
			log.debug(() -> "newSubscription: " + newSubscription);

			// (3) do validation in application layer
			if (!newSubscription.getEmailAddress().isValidFormat()) {
				throw new EmailFormatException("email.format");
			}

			// (4) validate state before changing
			if (newSubscription.isValidForValidated()) {
				newSubscription.toValidated();
			}

			// (3) do validation in application layer
			if (subscriptionService.isAlreadyExist(newSubscription.getEmailAddress())) {
				throw new EmailAlreadyExistException("email.duplicate");
			}

			// (4) validate state before changing
			if (newSubscription.isValidForConfirmed()) {
				newSubscription.toConfirmed(ZonedDateTime.now(Clock.systemDefaultZone()));
			}
			
			Subscription savedSubscription = subscriptionService.save(newSubscription);
			log.debug(() -> "savedSubscription: " + savedSubscription);
			
			// (5) demonstrate ACL
			SubscriptionDto savedSubscriptionDto = AntiCorruptionLayer.translate(savedSubscription);
			log.debug(() -> "savedSubscriptionDto: " + savedSubscriptionDto);
			
			return Optional.of(savedSubscriptionDto);
		} catch (EmailIsEmptyException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (EmailFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (EmailAlreadyExistException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (InvalidSubscriptionStatusException e) {
			throw new Exception("error.unknown");
		} catch (CannotCreateSubscriptionException e) {
			throw new Exception("error.unknown");
		} catch (Exception e) {
			throw new Exception("error.unknown");
		}
	}

	// (5) Anti-corruption layer (ACL)
	private static class AntiCorruptionLayer {

		public static SubscriptionDto translate(Subscription subscription) {
			log.debug(() -> "subscription: " + subscription);
			Objects.requireNonNull(subscription);

			return SubscriptionDto.builder()
				.id(subscription.getId().getValue())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus().toString())
				.subscribedAt(subscription.getSubscribedAt())
				.confirmedAt(subscription.getConfirmedAt())
				.build();
		}
	}

}
