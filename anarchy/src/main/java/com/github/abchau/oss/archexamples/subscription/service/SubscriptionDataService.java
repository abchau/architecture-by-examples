package com.github.abchau.oss.archexamples.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.github.abchau.oss.archexamples.subscription.model.Subscription;

@Repository
public interface SubscriptionDataService extends JpaRepository<Subscription, Long> {

	Subscription findByEmail(String email);

	@Modifying
    Subscription save(Subscription subscription);

}
