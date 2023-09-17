package com.abchau.archexamples.subscribe.infrastructure;

import com.abchau.archexamples.subscribe.domain.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	int countByEmail(String email);

	@Modifying
    Subscription save(Subscription subscription);

}
