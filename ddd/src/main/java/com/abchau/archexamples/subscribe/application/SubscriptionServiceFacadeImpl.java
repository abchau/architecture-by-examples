package com.abchau.archexamples.subscribe.application;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.subscribe.domain.model.subscription.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.subscribe.domain.model.subscription.SubscriptionService;

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
		log.trace("createSubscription()...invoked");
		log.debug("createSubscriptionCommand: {}", () -> createSubscriptionCommand);

		Objects.requireNonNull(createSubscriptionCommand);

		String email = createSubscriptionCommand.email();
		log.debug("email: {}", () -> email);

		// (3) do input validation in application layer
		if (email == null || "".equalsIgnoreCase(email)) {
			throw new IllegalArgumentException("email.empty");
		}

		try {
			Subscription newSubscription = Subscription.of(EmailAddress.of(email), ZonedDateTime.now(Clock.systemDefaultZone()));
			log.debug("newSubscription: {}", () -> newSubscription);

			// (3) do input validation in application layer
			if (!newSubscription.getEmailAddress().isValidFormat()) {
				throw new EmailFormatException("email.format");
			}

			// (4) validate state before changing
			if (newSubscription.isValidForValidated()) {
				newSubscription.toValidated();
			}

			Subscription savedSubscription = subscriptionService.createSubscription(newSubscription);
			log.debug("savedSubscription: {}", () -> savedSubscription);
			
			// (5) Anti-corruption layer (ACL)
			SubscriptionDto subscriptionDto = AntiCorruptionLayer.translate(savedSubscription);
			log.debug("subscriptionDto: {}", () -> subscriptionDto);
			
			return Optional.of(subscriptionDto);
		}
		// (6) demonstrate translating domain exception
		catch (EmailIsEmptyException | EmailFormatException | EmailAlreadyExistException | InvalidSubscriptionStatusException e) {
			log.error("known domain error. ", e);
			throw new IllegalArgumentException(e.getMessage());
		} 
		// (6) demonstrate translating domain exception
		catch (CannotCreateSubscriptionException e) {
			log.error("Unknown domain error. ", e);
			throw new Exception("error.unknown");
		} 
		// (6) demonstrate translating domain exception
		catch (Exception e) {
			log.error("Unknown domain error. ", e);
			throw new Exception("error.unknown");
		}
	}

	// (7) Anti-corruption layer (ACL)
	private static class AntiCorruptionLayer {

		public static SubscriptionDto translate(Subscription subscription) {
			log.debug("subscription: {}", () -> subscription);
			Objects.requireNonNull(subscription);

			return SubscriptionDto.builder()
				.id(subscription.getId().getValue())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus().toString())
				.subscribedAt(subscription.getSubscribedAt())
				.build();
		}

	}

}
