package com.abchau.archexamples.subscribe.repository;

import com.abchau.archexamples.subscribe.model.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	// (1) only pass and return absolute necessary
    int countByEmail(String email);

	@Modifying
    Subscription save(Subscription subscription);

}
