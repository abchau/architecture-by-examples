package com.abchau.archexamples.hddd.subscribe.infrastructure.persistence.jpa;

import com.abchau.archexamples.hddd.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hddd.subscribe.domain.output.SubscriptionPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JpaSubscriptionPersistenceAdapter implements SubscriptionPersistencePort {

	private SubscriptionPersistenceJpaRepository subscriptionPersistenceJpaRepository;

	@Autowired
	public JpaSubscriptionPersistenceAdapter(final SubscriptionPersistenceJpaRepository subscriptionPersistenceJpaRepository) {
		this.subscriptionPersistenceJpaRepository = subscriptionPersistenceJpaRepository;
	}

	@Override
	public int countByEmail(EmailAddress emailAddress) {
		log.trace(() -> "countByEmail()...invoked");

		String email = AntiCorruptionLayer.translateEmailAddress(emailAddress);
		log.debug(() -> "email: " + email);

		return subscriptionPersistenceJpaRepository.countByEmail(email);
	}

	@Override
	public Subscription save(Subscription subscription) {
		log.trace(() -> "save()...invoked");

		SubscriptionPersistence subscriptionPersistence = AntiCorruptionLayer.translateSubscription(subscription);
		log.debug(() -> "subscriptionPersistence: " + subscriptionPersistence);
		
		SubscriptionPersistence savedSubscriptionPersistence = subscriptionPersistenceJpaRepository.save(subscriptionPersistence);
		log.debug(() -> "savedSubscriptionPersistence: " + savedSubscriptionPersistence);
		
		Subscription savedSubscription = AntiCorruptionLayer.translateSubscription(savedSubscriptionPersistence);
		log.debug(() -> "savedSubscription: " + savedSubscription);

		return savedSubscription;
	}

	private static class AntiCorruptionLayer {

		public static EmailAddress translateEmailAddress(String email) {
			return EmailAddress.of(email);
		}

		public static String translateEmailAddress(EmailAddress emailAddress) {
			return emailAddress.getValue();
		}

		public static Subscription translateSubscription(SubscriptionPersistence subscriptionPersistence) {
			Subscription subscription = new Subscription();
			subscription.setId(subscriptionPersistence.getId());
			subscription.setEmailAddress(translateEmailAddress(subscriptionPersistence.getEmail()));
			subscription.setStatus(subscriptionPersistence.getStatus());
			subscription.setCreatedAt(subscriptionPersistence.getCreatedAt());
			subscription.setLastUpdatedAt(subscriptionPersistence.getLastUpdatedAt());

			return subscription;
		}

		public static SubscriptionPersistence translateSubscription(Subscription subscription) {
			SubscriptionPersistence subscriptionPersistence = new SubscriptionPersistence();
			subscriptionPersistence.setId(subscription.getId());
			subscriptionPersistence.setEmail(translateEmailAddress(subscription.getEmailAddress()));
			subscriptionPersistence.setStatus(subscription.getStatus());
			subscriptionPersistence.setCreatedAt(subscription.getCreatedAt());
			subscriptionPersistence.setLastUpdatedAt(subscription.getLastUpdatedAt());

			return subscriptionPersistence;
		}
	}

}
