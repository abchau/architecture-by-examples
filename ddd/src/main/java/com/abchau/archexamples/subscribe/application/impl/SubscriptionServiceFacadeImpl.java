package com.abchau.archexamples.subscribe.application.impl;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.subscribe.application.SubscriptionServiceFacade;
import com.abchau.archexamples.subscribe.application.dto.SubscriptionDto;
import com.abchau.archexamples.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.subscribe.domain.model.subscription.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.subscribe.domain.service.SubscriptionService;

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

		try {
			// (4) factory pattern
			Subscription newSubscription = Subscription.of(EmailAddress.of(email), ZonedDateTime.now(Clock.systemDefaultZone()));
			log.debug(() -> "newSubscription: " + newSubscription);

			// (3) do validation in application layer
			if (!newSubscription.getEmailAddress().isValidFormat()) {
				throw new EmailFormatException("email.format");
			}

			// (5) validate state before changing
			if (newSubscription.isValidForValidated()) {
				newSubscription.toValidated();
			}

			// (3) do validation in application layer
			if (subscriptionService.isAlreadyExist(newSubscription.getEmailAddress())) {
				throw new EmailAlreadyExistException("email.duplicate");
			}

			// (5) validate state before changing
			if (newSubscription.isValidForConfirmed()) {
				newSubscription.toConfirmed(ZonedDateTime.now(Clock.systemDefaultZone()));
			}
			
			Subscription savedSubscription = subscriptionService.save(newSubscription);
			log.debug(() -> "savedSubscription: " + savedSubscription);
			
			// (6) Anti-corruption layer (ACL)
			SubscriptionDto savedSubscriptionDto = AntiCorruptionLayer.translate(savedSubscription);
			log.debug(() -> "savedSubscriptionDto: " + savedSubscriptionDto);
			
			return Optional.of(savedSubscriptionDto);
		}
		// (7) demonstrate translating domain exception
		catch (EmailIsEmptyException | EmailFormatException | EmailAlreadyExistException | InvalidSubscriptionStatusException e) {
			log.error("known domain error. ", e);
			throw new IllegalArgumentException(e.getMessage());
		} 
		// (7) demonstrate translating domain exception
		catch (CannotCreateSubscriptionException e) {
			log.error("Unknown domain error. ", e);
			throw new Exception("error.unknown");
		} 
		// (7) demonstrate translating domain exception
		catch (Exception e) {
			log.error("Unknown domain error. ", e);
			throw new Exception("error.unknown");
		}
	}

	// (6) Anti-corruption layer (ACL)
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
