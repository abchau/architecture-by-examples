package com.abchau.archexamples.ddd.subscribe.infrastructure.persistence.jpa;

import java.util.Objects;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.SubscriptionRepository;
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
		Objects.requireNonNull(emailAddress);

		String email = emailAddress.getValue();
		log.debug(() -> "email: " + email);

		return subscriptionPersistenceJpaRepository.countByEmail(email);
	}

	@Override
	public Subscription save(Subscription subscription) {
		log.trace(() -> "save()...invoked");
		Objects.requireNonNull(subscription);

		SubscriptionPersistence subscriptionPersistence = AntiCorruptionLayer.translate(subscription);
		log.debug(() -> "subscriptionPersistence: " + subscriptionPersistence);

		SubscriptionPersistence savedSubscriptionPersistence = subscriptionPersistenceJpaRepository.save(subscriptionPersistence);
		log.debug(() -> "savedSubscriptionPersistence: " + savedSubscriptionPersistence);

		Subscription savedSubscription = AntiCorruptionLayer.translate(savedSubscriptionPersistence);
		log.debug(() -> "savedSubscription: " + savedSubscription);

		return savedSubscription;
	}

	private static class AntiCorruptionLayer {

		public static Subscription translate(SubscriptionPersistence subscriptionPersistence) {
			Objects.requireNonNull(subscriptionPersistence);

			Subscription subscription = new Subscription();
			subscription.setId(subscriptionPersistence.getId());
			subscription.setEmailAddress(EmailAddress.of(subscriptionPersistence.getEmail()));
			subscription.setStatus(subscriptionPersistence.getStatus());
			subscription.setCreatedAt(subscriptionPersistence.getCreatedAt());
			subscription.setLastUpdatedAt(subscriptionPersistence.getLastUpdatedAt());

			return subscription;
		}

		public static SubscriptionPersistence translate(Subscription subscription) {
			Objects.requireNonNull(subscription);

			SubscriptionPersistence subscriptionPersistence = new SubscriptionPersistence();
			subscriptionPersistence.setId(subscription.getId());
			subscriptionPersistence.setEmail(subscription.getEmailAddress().toString());
			subscriptionPersistence.setStatus(subscription.getStatus());
			subscriptionPersistence.setCreatedAt(subscription.getCreatedAt());
			subscriptionPersistence.setLastUpdatedAt(subscription.getLastUpdatedAt());

			return subscriptionPersistence;
		}
	}

}
