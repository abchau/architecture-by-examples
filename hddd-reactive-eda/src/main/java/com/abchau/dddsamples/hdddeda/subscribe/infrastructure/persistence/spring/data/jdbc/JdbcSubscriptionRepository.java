package com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.jdbc;

import java.util.Optional;

import com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.jdbc.SubscriptionEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @deprecated just for demostration purpose
 */
@Deprecated
@Repository
public interface JdbcSubscriptionRepository extends CrudRepository<SubscriptionEntity, byte[]> {

    public SubscriptionEntity save(final SubscriptionEntity SubscriptionEntity);

}
