package com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.r2dbc;

import java.util.UUID;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hdddeda.subscribe.domain.output.SubscriptionPersistencePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
final class SpringDataR2dbcSubscriptionPersistenceService implements SubscriptionPersistencePort {

	private R2dbcSubscriptionRepository r2dbcSubscriptionRepository;

	@Autowired
	public SpringDataR2dbcSubscriptionPersistenceService(
			final R2dbcSubscriptionRepository r2dbcSubscriptionRepository) {
		this.r2dbcSubscriptionRepository = r2dbcSubscriptionRepository;
	}

	@Override
	public Mono<Subscription> save(final Subscription subscription) {
		return Mono.just(subscription)
				// map to technology-specific Persistence object
				.map(Translator::toDatabaseEntity)
				// do technology-specific save operation
				.flatMap(r2dbcSubscriptionRepository::save)
				// map back to Domain object
				.map(Translator::toDomainEntity);
	}

	/**
	 * Anti-corruption Layer
	 */
	private static class Translator {

		/**
		 * Convert a Domain entity to Database entity
		 * 
		 * @param Subscription
		 * @return
		 */
		public static SubscriptionEntity toDatabaseEntity(final Subscription subscription) {
			return SubscriptionEntity.builder()
					.id(subscription.getId().toString().getBytes())
					.email(subscription.getEmailAddress().getValue())
					.status(subscription.getStatus().toString())
					.createdAt(subscription.getCreatedAt())
					.lastUpdatedAt(subscription.getLastUpdatedAt())
					.version(subscription.getVersion())
					.build();
		}

		/**
		 * Convert a Database entity to Domain entity
		 * 
		 * @param SubscriptionEntity
		 * @return
		 */
		public static Subscription toDomainEntity(final SubscriptionEntity SubscriptionEntity) {
			return Subscription.builder()
					.id(UUID.fromString(new String(SubscriptionEntity.getId())))
					.emailAddress(EmailAddress.of(SubscriptionEntity.getEmail()))
					.status(Subscription.Status.valueOf(SubscriptionEntity.getStatus()))
					.createdAt(SubscriptionEntity.getCreatedAt())
					.lastUpdatedAt(SubscriptionEntity.getLastUpdatedAt())
					.version(SubscriptionEntity.getVersion())
					.build();
		}
	}

}
