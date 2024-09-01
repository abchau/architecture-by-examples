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
			.map(JPA_TO_DOMAIN)
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
			SubscriptionJpaEntity subscriptionJpaEntity = DOMAIN_TO_JPA.apply(subscription);
			SubscriptionJpaEntity savedSubscriptionJpaEntity = subscriptionJpaRepository.save(subscriptionJpaEntity);
			Subscription savedSubscription = JPA_TO_DOMAIN.apply(savedSubscriptionJpaEntity);
	
			return savedSubscription;
		} catch(Exception e) {
			throw new SubscriptionPersistenceException("couldn't save", e);
		}
	}

	static final Function<SubscriptionJpaEntity, Subscription> JPA_TO_DOMAIN = (jpaEntity) -> {
		return Subscription.builder()
			.id(SubscriptionId.of(jpaEntity.getId()))
			.emailAddress(EmailAddress.of(jpaEntity.getEmail()))
			.status(Subscription.Status.valueOf(jpaEntity.getStatus()))
			.createdAt(jpaEntity.getCreatedAt())
			.build();
	};

	static final Function<Subscription, SubscriptionJpaEntity> DOMAIN_TO_JPA = (domainEntity) -> {
		return SubscriptionJpaEntity.builder()
			.id(domainEntity.getId() == null ? null : domainEntity.getId().getValue())
			.email(domainEntity.getEmailAddress().getValue())
			.status(domainEntity.getStatus().toString())
			.createdAt(domainEntity.getCreatedAt())
			.build();
	};
}
