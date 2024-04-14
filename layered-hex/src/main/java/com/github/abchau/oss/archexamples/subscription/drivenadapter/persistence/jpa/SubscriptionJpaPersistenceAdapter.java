package com.github.abchau.oss.archexamples.subscription.drivenadapter.persistence.jpa;

import java.util.Objects;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.stereotype.Component;

import com.github.abchau.oss.archexamples.subscription.application.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;
import com.github.abchau.oss.archexamples.subscription.application.drivenport.EmailVerificationPort;
import com.github.abchau.oss.archexamples.subscription.application.drivenport.SaveSubscriptionPort;

import lombok.extern.slf4j.Slf4j;

@SecondaryAdapter
@Slf4j
@Component
class SubscriptionJpaPersistenceAdapter implements SaveSubscriptionPort, EmailVerificationPort {

	private SubscriptionJpaRepository subscriptionPersistenceJpaRepository;

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
