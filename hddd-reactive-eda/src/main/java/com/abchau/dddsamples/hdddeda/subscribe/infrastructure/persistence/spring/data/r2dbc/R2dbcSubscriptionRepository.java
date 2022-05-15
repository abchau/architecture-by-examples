package com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.r2dbc;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface R2dbcSubscriptionRepository extends ReactiveCrudRepository<SubscriptionEntity, byte[]> {

	@Modifying
    public Mono<SubscriptionEntity> save(final SubscriptionEntity SubscriptionEntity);

}
