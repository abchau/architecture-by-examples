package com.abchau.archexamples.subscribe.drivenadapter.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
interface SubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, Long> {

    int countByEmail(String email);

	@Modifying
    SubscriptionJpaEntity save(SubscriptionJpaEntity subscriptionPersistence);

}
