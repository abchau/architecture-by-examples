package com.abchau.archexamples.subscribe.outputadapter.persistence.jpa;

import java.util.Objects;

import com.abchau.archexamples.subscribe.application.core.EmailAddress;
import com.abchau.archexamples.subscribe.application.core.Subscription;
import com.abchau.archexamples.subscribe.application.core.SubscriptionPersistencePort;

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
		log.trace("countByEmail()...invoked");
		Objects.requireNonNull(emailAddress, "email.empty");
		Objects.requireNonNull(emailAddress.getValue(), "email.empty");

		String email = emailAddress.getValue();
		log.debug("email: {}", () -> email);

		return subscriptionPersistenceJpaRepository.countByEmail(email);
	}

	@Override
	public Subscription save(Subscription subscription) {
		log.trace("save()...invoked");
		Objects.requireNonNull(subscription);
		Objects.requireNonNull(subscription.getEmailAddress(), "email.empty");
		Objects.requireNonNull(subscription.getEmailAddress().getValue(), "email.empty");

		SubscriptionPersistence subscriptionPersistence = AntiCorruptionLayer.translate(subscription);
		log.debug("subscriptionPersistence: {}", () -> subscriptionPersistence);

		SubscriptionPersistence savedSubscriptionPersistence = subscriptionPersistenceJpaRepository.save(subscriptionPersistence);
		log.debug("savedSubscriptionPersistence: {}", () -> savedSubscriptionPersistence);

		Subscription savedSubscription = AntiCorruptionLayer.translate(savedSubscriptionPersistence);
		log.debug("savedSubscription: {}", () -> savedSubscription);

		return savedSubscription;
	}

	private static class AntiCorruptionLayer {

		public static Subscription translate(SubscriptionPersistence subscriptionPersistence) {
			Objects.requireNonNull(subscriptionPersistence);
	
			return Subscription.builder()
				.id(subscriptionPersistence.getId())
				.emailAddress(EmailAddress.of(subscriptionPersistence.getEmail()))
				.status(subscriptionPersistence.getStatus())
				.createdAt(subscriptionPersistence.getCreatedAt())
				.build();
		}

		public static SubscriptionPersistence translate(Subscription subscription) {
			Objects.requireNonNull(subscription);
	
			return SubscriptionPersistence.builder()
				.id(subscription.getId())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus())
				.createdAt(subscription.getCreatedAt())
				.build();
		}
	}

}
