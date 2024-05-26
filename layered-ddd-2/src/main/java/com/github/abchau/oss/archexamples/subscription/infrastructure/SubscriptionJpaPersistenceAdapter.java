package com.github.abchau.oss.archexamples.subscription.infrastructure;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.domain.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionId;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionPersistenceException;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionDataStore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
final class SubscriptionJpaPersistenceAdapter implements SubscriptionDataStore {

	private SubscriptionJpaRepository subscriptionJpaRepository;

	public SubscriptionJpaPersistenceAdapter(final SubscriptionJpaRepository subscriptionJpaRepository) {
		this.subscriptionJpaRepository = subscriptionJpaRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Subscription> findAll() {
		return subscriptionJpaRepository.findAll().stream()
			.map(PersistenceModelToDomainModel)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public int countByEmail(EmailAddress emailAddress) {
		Objects.requireNonNull(emailAddress);

		String email = emailAddress.getValue();

		return subscriptionJpaRepository.countByEmail(email);
	}

	@Transactional
	@Override
	public Subscription save(Subscription subscription) {
		Objects.requireNonNull(subscription);

		try {
			SubscriptionJpaEntity subscriptionJpaEntity = DomainModelToPersistenceModel.apply(subscription);
			SubscriptionJpaEntity savedSubscriptionJpaEntity = subscriptionJpaRepository.save(subscriptionJpaEntity);
			Subscription savedSubscription = PersistenceModelToDomainModel.apply(savedSubscriptionJpaEntity);
	
			return savedSubscription;
		} catch(Exception e) {
			throw new SubscriptionPersistenceException("couldn't save", e);
		}
	}

	Function<SubscriptionJpaEntity, Subscription> PersistenceModelToDomainModel = (subscriptionJpaEntity) -> {
		return Subscription.builder()
			.id(SubscriptionId.of(subscriptionJpaEntity.getId()))
			.emailAddress(EmailAddress.of(subscriptionJpaEntity.getEmail()))
			.status(Subscription.Status.valueOf(subscriptionJpaEntity.getStatus()))
			.createdAt(subscriptionJpaEntity.getCreatedAt())
			.build();
	};

	Function<Subscription, SubscriptionJpaEntity> DomainModelToPersistenceModel = (subscription) -> {
		return SubscriptionJpaEntity.builder()
			.id(subscription.getId() == null ? null : subscription.getId().getValue())
			.email(subscription.getEmailAddress().getValue())
			.status(subscription.getStatus().toString())
			.createdAt(subscription.getCreatedAt())
			.build();
	};
}
