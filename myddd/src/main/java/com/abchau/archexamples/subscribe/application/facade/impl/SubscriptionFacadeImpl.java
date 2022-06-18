package com.abchau.archexamples.subscribe.application.facade.impl;

import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.subscribe.application.dto.SubscriptionDto;
import com.abchau.archexamples.subscribe.application.facade.SubscriptionFacade;
import com.abchau.archexamples.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.subscribe.domain.exception.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.exception.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.input.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionFacadeImpl implements SubscriptionFacade {

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscriptionFacadeImpl(final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	// (1) tasks coordination and wrap tasks in a transaction
	@Transactional
	@Override
	// (1) better method name
	public Optional<SubscriptionDto> createSubscription(CreateSubscriptionCommand createSubscriptionCommand) throws IllegalArgumentException, Exception {
		log.trace("createSubscription()...invoked");
		Objects.requireNonNull(createSubscriptionCommand);

		String email = createSubscriptionCommand.getEmail();
		log.debug("email: {}", () -> email);

		// (3) also do validation here
		if (email == null || "".equalsIgnoreCase(email)) {
			throw new IllegalArgumentException("email.empty");
		}

		try {
			EmailAddress emailAddress = EmailAddress.of(email);
			subscriptionService.isEmailAlreadyExist(emailAddress);

			Subscription subscription = Subscription.of(emailAddress);
			log.debug("subscription: {}", () -> subscription);
			
			Subscription savedSubscription = subscriptionService.createSubscription(subscription)
				.orElseThrow();
			log.debug("savedSubscription: {}", () -> savedSubscription);
			
			// (3) demonstrate ACL
			SubscriptionDto subscriptionDto = AntiCorruptionLayer.translate(savedSubscription);
			log.debug("subscriptionDto: {}", () -> subscriptionDto);
			
			return Optional.of(subscriptionDto);
		} 
		// (4) demonstrate translating domain exception
		catch (NullPointerException e) {
			throw new IllegalArgumentException("email.empty");
		}
		// (4) demonstrate translating domain exception
		catch (EmailFormatException e) {
			throw new IllegalArgumentException("email.format");
		} 
		// (4) demonstrate translating domain exception
		catch (EmailAlreadyExistException e) {
			throw new IllegalArgumentException("email.duplicate");
		} 
		// (4) demonstrate translating domain exception
		catch (Exception e) {
			throw new Exception("error.unknown");
		}
	}

	// (5) Anti-corruption layer (ACL)
	private static class AntiCorruptionLayer {

		public static SubscriptionDto translate(Subscription subscription) {
			Objects.requireNonNull(subscription);

			return SubscriptionDto.builder()
				.id(subscription.getId())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus())
				.createdAt(subscription.getCreatedAt())
				.build();
		}
	}

}
