package com.github.abchau.oss.archexamples.subscription.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.model.Subscription;
import com.github.abchau.oss.archexamples.subscription.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	public SubscriptionService(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}
	
	@Transactional
	public Subscription save(String email) {

		if (!Subscription.isEmailValid(email)) {
			throw new IllegalArgumentException("email.format");
		} 
		
		int count = subscriptionRepository.countByEmail(email);
		if (count > 0) {
			throw new IllegalArgumentException("email.duplicate");
		} 

		Subscription subscription = Subscription.of(email);

		return subscriptionRepository.save(subscription);
	}

}
