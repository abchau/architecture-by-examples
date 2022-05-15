package com.abchau.archexamples.ddd.subscribe.infrastructure.persistence.jpa;

import java.util.Objects;
import java.util.Optional;

import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.ddd.subscribe.domain.model.subscription.EmailPersistenceErrorException;
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
		Objects.requireNonNull(emailAddress.getValue());

		String email = emailAddress.getValue();
		log.debug(() -> "email: " + email);

		return subscriptionPersistenceJpaRepository.countByEmail(email);
	}

	@Override
	public Subscription save(Subscription subscription) throws EmailPersistenceErrorException {
		log.trace(() -> "save()...invoked");
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
			log.error("couldn't persist data", e);
			throw new EmailPersistenceErrorException("error.persistence");
		}
	}

	private static class AntiCorruptionLayer {

		public static Subscription translate(SubscriptionPersistence subscriptionPersistence) {
			Objects.requireNonNull(subscriptionPersistence);

			return Subscription.builder()
				.id(subscriptionPersistence.getId())
				.emailAddress(EmailAddress.of(subscriptionPersistence.getEmail()))
				.status(subscriptionPersistence.getStatus())
				.createdAt(subscriptionPersistence.getCreatedAt())
				.lastUpdatedAt(subscriptionPersistence.getLastUpdatedAt())
				.build();
		}

		public static SubscriptionPersistence translate(Subscription subscription) {
			Objects.requireNonNull(subscription);

			return SubscriptionPersistence.builder()
				.id(subscription.getId())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus())
				.createdAt(subscription.getCreatedAt())
				.lastUpdatedAt(subscription.getLastUpdatedAt())
				.build();
		}
	}

}
