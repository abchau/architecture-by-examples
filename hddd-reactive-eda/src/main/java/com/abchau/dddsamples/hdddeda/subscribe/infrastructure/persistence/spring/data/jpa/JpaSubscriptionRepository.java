package com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @deprecated just for demostration purpose
 */
@Deprecated
@Repository
public interface JpaSubscriptionRepository extends CrudRepository<SubscriptionEntity, byte[]> {

    public SubscriptionEntity save(final SubscriptionEntity SubscriptionEntity);

}
