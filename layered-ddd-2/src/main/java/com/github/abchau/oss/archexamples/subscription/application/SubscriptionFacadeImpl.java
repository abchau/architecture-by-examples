package com.github.abchau.oss.archexamples.subscription.application;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.domain.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.domain.EmailAddressVerifier;
import com.github.abchau.oss.archexamples.subscription.domain.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.domain.EmailFormatException;
import com.github.abchau.oss.archexamples.subscription.domain.EmailIsEmptyException;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionPersistenceException;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class SubscriptionFacadeImpl implements SubscriptionFacade {

	private final EmailAddressVerifier emailAddressVerifier;

	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionFacadeImpl(
		final EmailAddressVerifier emailAddressVerifier, 
		final SubscriptionRepository subscriptionRepository
	) {
		this.emailAddressVerifier = emailAddressVerifier;
		this.subscriptionRepository = subscriptionRepository;
	}

	@Transactional(readOnly = true)
	public List<Subscription> showSubscription() {
		return subscriptionRepository.findAll();
	}

	@Transactional
	@Override
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
			log.error("known domain error. ", e);
			throw new IllegalArgumentException(e.getMessage());
		} 
		catch (SubscriptionPersistenceException e) {
			log.error("known domain error. ", e);
			throw new RuntimeException("error.unknown");
		} 
		catch (Exception e) {
			log.error("Unknown domain error. ", e);
			throw new RuntimeException("error.unknown");
		}
	}

}
