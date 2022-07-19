package com.abchau.archexamples.subscribe.outputadapter.persistence.jpa;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.application.outputport.CreateSubscriptionPersistencePort;
import com.abchau.archexamples.subscribe.application.outputport.QuerySubscriptionPersistencePort;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SubscriptionJpaPersistenceAdapter implements CreateSubscriptionPersistencePort, QuerySubscriptionPersistencePort {

	private SubscriptionJpaRepository subscriptionPersistenceJpaRepository;

	@Autowired
	public SubscriptionJpaPersistenceAdapter(final SubscriptionJpaRepository subscriptionPersistenceJpaRepository) {
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

		SubscriptionJpaEntity subscriptionPersistence = AntiCorruptionLayer.translate(subscription);
		log.debug("subscriptionPersistence: {}", () -> subscriptionPersistence);

		SubscriptionJpaEntity savedSubscriptionPersistence = subscriptionPersistenceJpaRepository.save(subscriptionPersistence);
		log.debug("savedSubscriptionPersistence: {}", () -> savedSubscriptionPersistence);

		Subscription savedSubscription = AntiCorruptionLayer.translate(savedSubscriptionPersistence);
		log.debug("savedSubscription: {}", () -> savedSubscription);

		return savedSubscription;
	}

	private static class AntiCorruptionLayer {

		public static Subscription translate(SubscriptionJpaEntity subscriptionPersistence) {
			Objects.requireNonNull(subscriptionPersistence);
	
			return Subscription.builder()
				.id(subscriptionPersistence.getId())
				.emailAddress(EmailAddress.of(subscriptionPersistence.getEmail()))
				.status(subscriptionPersistence.getStatus())
				.createdAt(subscriptionPersistence.getCreatedAt())
				.build();
		}

		public static SubscriptionJpaEntity translate(Subscription subscription) {
			Objects.requireNonNull(subscription);
	
			return SubscriptionJpaEntity.builder()
				.id(subscription.getId())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus())
				.createdAt(subscription.getCreatedAt())
				.build();
		}
	}

}
