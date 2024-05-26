package com.github.abchau.oss.archexamples.subscription.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.domain.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.domain.EmailAddressVerifier;
import com.github.abchau.oss.archexamples.subscription.domain.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.domain.EmailFormatException;
import com.github.abchau.oss.archexamples.subscription.domain.EmailIsEmptyException;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionPersistenceException;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionDataStore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubscriptionFacade {

	private final EmailAddressVerifier emailAddressVerifier;

	private final SubscriptionDataStore subscriptionRepository;

	public SubscriptionFacade(
		final EmailAddressVerifier emailAddressVerifier, 
		final SubscriptionDataStore subscriptionRepository
	) {
		this.emailAddressVerifier = emailAddressVerifier;
		this.subscriptionRepository = subscriptionRepository;
	}

	@Transactional(readOnly = true)
	public List<Subscription> showSubscription() {
		return subscriptionRepository.findAll();
	}

	@Transactional
	public Subscription createSubscription(CreateSubscriptionCommand createSubscriptionCommand) {
		try {
			EmailAddress emailAddress = EmailAddress.of(createSubscriptionCommand.email());
			
			if (emailAddressVerifier.isEmailAlreadyExist(emailAddress)) {
				throw new EmailAlreadyExistException("email.duplicate");
			}

			Subscription newSubscription = Subscription.of(emailAddress, LocalDateTime.now());
			Subscription savedSubscription = subscriptionRepository.save(newSubscription);
			
			return savedSubscription;
		}
		catch (EmailIsEmptyException | EmailFormatException | EmailAlreadyExistException e) {
			log.error("known domain error", e);
			throw new IllegalArgumentException(e.getMessage());
		} 
		catch (SubscriptionPersistenceException e) {
			log.error("known domain error", e);
			throw new RuntimeException("error.unknown");
		} 
		catch (Exception e) {
			log.error("Unknown domain error", e);
			throw new RuntimeException("error.unknown");
		}
	}
	
	public static record CreateSubscriptionCommand(String email) {
		public static CreateSubscriptionCommand of(String email) {
			return new CreateSubscriptionCommand(email);
		}
	}

}
