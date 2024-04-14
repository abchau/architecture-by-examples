package com.github.abchau.oss.archexamples.subscription.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
interface SubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, Long> {

	List<SubscriptionJpaEntity> findAll();
	
    int countByEmail(String email);

	@Modifying
    SubscriptionJpaEntity save(SubscriptionJpaEntity subscriptionJpaEntity);

}
