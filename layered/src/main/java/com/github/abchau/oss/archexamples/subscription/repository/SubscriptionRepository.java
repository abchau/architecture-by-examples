package com.github.abchau.oss.archexamples.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.github.abchau.oss.archexamples.subscription.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	int countByEmail(String email);

	@Modifying
    Subscription save(Subscription subscription);

}
