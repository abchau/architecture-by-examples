package com.abchau.archexamples.subscribe.drivenadapter.persistence.jpa;

import java.util.Objects;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abchau.archexamples.subscribe.application.drivenport.EmailVerificationPort;
import com.abchau.archexamples.subscribe.application.drivenport.SaveSubscriptionPort;
import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.Subscription;

import lombok.extern.log4j.Log4j2;

@SecondaryAdapter
@Log4j2
@Component
class SubscriptionJpaPersistenceAdapter implements SaveSubscriptionPort, EmailVerificationPort {

	private SubscriptionJpaRepository subscriptionPersistenceJpaRepository;

	@Autowired
	public SubscriptionJpaPersistenceAdapter(final SubscriptionJpaRepository subscriptionPersistenceJpaRepository) {
		this.subscriptionPersistenceJpaRepository = subscriptionPersistenceJpaRepository;
	}

	@Override
	public boolean isEmailAlreadyExist(EmailAddress emailAddress) {
		Objects.requireNonNull(emailAddress, "email.empty");
		Objects.requireNonNull(emailAddress.getValue(), "email.empty");

		String email = emailAddress.getValue();

		int count = subscriptionPersistenceJpaRepository.countByEmail(email);

		return count > 0;
	}

	@Override
	public Subscription save(Subscription subscription) {
		Objects.requireNonNull(subscription);
		Objects.requireNonNull(subscription.getEmailAddress(), "email.empty");
		Objects.requireNonNull(subscription.getEmailAddress().getValue(), "email.empty");

		SubscriptionJpaEntity subscriptionPersistence = translateToPersistenceModel(subscription);

		SubscriptionJpaEntity savedSubscriptionPersistence = subscriptionPersistenceJpaRepository.save(subscriptionPersistence);

		Subscription savedSubscription = translateToDomainModel(savedSubscriptionPersistence);

		return savedSubscription;
	}

		 static Subscription translateToDomainModel(SubscriptionJpaEntity subscriptionPersistence) {
			Objects.requireNonNull(subscriptionPersistence);
	
			return Subscription.builder()
				.id(subscriptionPersistence.getId())
				.emailAddress(EmailAddress.of(subscriptionPersistence.getEmail()))
				.status(subscriptionPersistence.getStatus())
				.createdAt(subscriptionPersistence.getCreatedAt())
				.build();
		}

		 static SubscriptionJpaEntity translateToPersistenceModel(Subscription subscription) {
			Objects.requireNonNull(subscription);
	
			return SubscriptionJpaEntity.builder()
				.id(subscription.getId())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus())
				.createdAt(subscription.getCreatedAt())
				.build();
		}

}
