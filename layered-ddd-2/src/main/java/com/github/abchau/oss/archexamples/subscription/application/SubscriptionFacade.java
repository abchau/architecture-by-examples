package com.github.abchau.oss.archexamples.subscription.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.domain.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.domain.EmailAddressVerifier;
import com.github.abchau.oss.archexamples.subscription.domain.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionDataStore;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionException;

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
		EmailAddress emailAddress = EmailAddress.of(createSubscriptionCommand.email());
		if (emailAddressVerifier.isEmailAlreadyExist(emailAddress)) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		Subscription newSubscription = Subscription.ofNew(emailAddress, createSubscriptionCommand.createdAt());
		Subscription savedSubscription = subscriptionRepository.save(newSubscription);
			
		return savedSubscription;
	}
	
	public static record CreateSubscriptionCommand(
		String email,
		LocalDateTime createdAt
	) {
		public static CreateSubscriptionCommand of(String email) {
			return new CreateSubscriptionCommand(email, LocalDateTime.now());
		}
	}

}
