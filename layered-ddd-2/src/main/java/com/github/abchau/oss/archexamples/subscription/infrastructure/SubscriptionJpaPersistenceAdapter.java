package com.github.abchau.oss.archexamples.subscription.infrastructure;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.abchau.oss.archexamples.subscription.domain.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionId;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionPersistenceException;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
final class SubscriptionJpaPersistenceAdapter implements SubscriptionRepository {

	private SubscriptionJpaRepository subscriptionJpaRepository;

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
			.status(Subscription.Status.valueOf(subscriptionJpaEntity.getStatus()))
			.createdAt(subscriptionJpaEntity.getCreatedAt())
			.build();
	}

	static SubscriptionJpaEntity translateToPersistenceModel(Subscription subscription) {
		Objects.requireNonNull(subscription);

		return SubscriptionJpaEntity.builder()
			.id(subscription.getId() == null ? null : subscription.getId().getValue())
			.email(subscription.getEmailAddress().getValue())
			.status(subscription.getStatus().toString())
			.createdAt(subscription.getCreatedAt())
			.build();
	}
}
