package com.abchau.archexamples.subscribe.outputadapter.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

// (1) if use hibernate directly, this class won't exist
@Repository
interface SubscriptionPersistenceJpaRepository extends JpaRepository<SubscriptionPersistence, Long> {

    int countByEmail(String email);

	@Modifying
    SubscriptionPersistence save(SubscriptionPersistence subscriptionPersistence);

}
