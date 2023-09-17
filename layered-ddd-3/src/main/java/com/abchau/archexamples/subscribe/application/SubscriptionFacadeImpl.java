package com.abchau.archexamples.subscribe.application;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.EmailAddressVerifier;
import com.abchau.archexamples.subscribe.domain.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.EmailIsEmptyException;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.domain.SubscriptionPersistenceException;
import com.abchau.archexamples.subscribe.domain.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
/*final*/ class SubscriptionFacadeImpl implements SubscriptionFacade {

	private final EmailAddressVerifier emailAddressVerifier;

	private final SubscriptionRepository subscriptionRepository;

	@Autowired
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
