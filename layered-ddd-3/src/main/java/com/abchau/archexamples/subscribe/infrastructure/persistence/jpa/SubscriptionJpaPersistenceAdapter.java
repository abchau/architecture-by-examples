package com.abchau.archexamples.subscribe.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.SubscriptionPersistenceException;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.domain.SubscriptionId;
import com.abchau.archexamples.subscribe.domain.SubscriptionRepository;
import com.abchau.archexamples.subscribe.domain.SubscriptionStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
final class SubscriptionJpaPersistenceAdapter implements SubscriptionRepository {

	private SubscriptionJpaRepository subscriptionJpaRepository;

	@Autowired
	public SubscriptionJpaPersistenceAdapter(final SubscriptionJpaRepository subscriptionJpaRepository) {
		this.subscriptionJpaRepository = subscriptionJpaRepository;
	}

	@Override
	public List<Subscription> findAll() {
		return subscriptionJpaRepository.findAll().stream()
			.map(SubscriptionJpaPersistenceAdapter::translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	public int countByEmail(EmailAddress emailAddress) {
		Objects.requireNonNull(emailAddress);
		Objects.requireNonNull(emailAddress.getValue());

		String email = emailAddress.getValue();

		return subscriptionJpaRepository.countByEmail(email);
	}

	@Override
	public Subscription save(Subscription subscription) {
		Objects.requireNonNull(subscription);
		Objects.requireNonNull(subscription.getEmailAddress());
		Objects.requireNonNull(subscription).getEmailAddress().getValue();

		try {
			SubscriptionJpaEntity subscriptionJpaEntity = translateToPersistenceModel(subscription);
	
			SubscriptionJpaEntity savedSubscriptionJpaEntity = subscriptionJpaRepository.save(subscriptionJpaEntity);
	
			Subscription savedSubscription = translateToDomainModel(savedSubscriptionJpaEntity);
	
			return savedSubscription;
		} catch(Exception e) {
			throw new SubscriptionPersistenceException("couldn't save", e);
		}
	}

	static Subscription translateToDomainModel(SubscriptionJpaEntity subscriptionJpaEntity) {
		Objects.requireNonNull(subscriptionJpaEntity);

		return Subscription.builder()
			.id(SubscriptionId.of(subscriptionJpaEntity.getId()))
			.emailAddress(EmailAddress.of(subscriptionJpaEntity.getEmail()))
			.status(SubscriptionStatus.valueOf(subscriptionJpaEntity.getStatus()))
			.subscribedAt(subscriptionJpaEntity.getSubscribedAt())
			.build();
	}

	static SubscriptionJpaEntity translateToPersistenceModel(Subscription subscription) {
		Objects.requireNonNull(subscription);

		return SubscriptionJpaEntity.builder()
			.id(subscription.getId() == null ? null : subscription.getId().getValue())
			.email(subscription.getEmailAddress().getValue())
			.status(subscription.getStatus().toString())
			.subscribedAt(subscription.getSubscribedAt())
			.build();
	}
}
