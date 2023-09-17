package com.abchau.archexamples.subscribe.service;

import com.abchau.archexamples.subscribe.model.Subscription;
import com.abchau.archexamples.subscribe.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	@Autowired
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
