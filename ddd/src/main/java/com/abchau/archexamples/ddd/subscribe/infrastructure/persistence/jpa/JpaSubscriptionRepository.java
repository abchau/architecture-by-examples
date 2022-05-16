package com.abchau.archexamples.ddd.subscribe.infrastructure.persistence.jpa;

import java.util.Objects;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.SubscriptionId;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.SubscriptionRepository;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.SubscriptionStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JpaSubscriptionRepository implements SubscriptionRepository {

	private SubscriptionPersistenceJpaRepository subscriptionPersistenceJpaRepository;

	@Autowired
	public JpaSubscriptionRepository(final SubscriptionPersistenceJpaRepository subscriptionPersistenceJpaRepository) {
		this.subscriptionPersistenceJpaRepository = subscriptionPersistenceJpaRepository;
	}

	@Override
	public int countByEmail(EmailAddress emailAddress) {
		log.trace(() -> "countByEmail()...invoked");
		log.debug(() -> "emailAddress: " + emailAddress);

		Objects.requireNonNull(emailAddress);
		Objects.requireNonNull(emailAddress.getValue());

		String email = emailAddress.getValue();
		log.debug(() -> "email: " + email);

		return subscriptionPersistenceJpaRepository.countByEmail(email);
	}

	@Override
	public Subscription save(Subscription subscription) {
		log.trace(() -> "save()...invoked");
		log.debug(() -> "subscription: " + subscription);

		Objects.requireNonNull(subscription);
		Objects.requireNonNull(subscription.getEmailAddress());
		Objects.requireNonNull(subscription).getEmailAddress().getValue();

		try {
			SubscriptionPersistence subscriptionPersistence = AntiCorruptionLayer.translate(subscription);
			log.debug(() -> "subscriptionPersistence: " + subscriptionPersistence);
	
			SubscriptionPersistence savedSubscriptionPersistence = subscriptionPersistenceJpaRepository.save(subscriptionPersistence);
			log.debug(() -> "savedSubscriptionPersistence: " + savedSubscriptionPersistence);
	
			Subscription savedSubscription = AntiCorruptionLayer.translate(savedSubscriptionPersistence);
			log.debug(() -> "savedSubscription: " + savedSubscription);
	
			return savedSubscription;
		} catch(Exception e) {
			log.error("Unknown infrastructure error", e);
			throw e;
		}
	}

	private static class AntiCorruptionLayer {

		public static Subscription translate(SubscriptionPersistence subscriptionPersistence) {
			log.debug(() -> "subscriptionPersistence: " + subscriptionPersistence);
			Objects.requireNonNull(subscriptionPersistence);

			return Subscription.builder()
				.id(SubscriptionId.of(subscriptionPersistence.getId()))
				.emailAddress(EmailAddress.of(subscriptionPersistence.getEmail()))
				.status(SubscriptionStatus.valueOf(subscriptionPersistence.getStatus()))
				.subscribedAt(subscriptionPersistence.getSubscribedAt())
				.confirmedAt(subscriptionPersistence.getConfirmedAt())
				.build();
		}

		public static SubscriptionPersistence translate(Subscription subscription) {
			log.debug(() -> "subscription: " + subscription);
			Objects.requireNonNull(subscription);

			return SubscriptionPersistence.builder()
				.id(subscription.getId() == null ? null : subscription.getId().getValue())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus().toString())
				.subscribedAt(subscription.getSubscribedAt())
				.confirmedAt(subscription.getConfirmedAt())
				.build();
		}
	}

}
