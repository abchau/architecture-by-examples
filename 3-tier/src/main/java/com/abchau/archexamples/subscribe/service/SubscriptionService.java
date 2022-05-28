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

	// (1) only return absolute necessary
	@Transactional(readOnly = true)
    public boolean isAlreadyExist(String email) {
		log.trace(() -> "isAlreadyExist()...invoked");
		log.debug(() -> "email: " + email);

		int count = subscriptionRepository.countByEmail(email);
		log.debug(() -> "count: " + count);
		
		return count > 0;
	}
	
	@Transactional
	public Subscription save(Subscription subscription) throws Exception {
		log.trace(() -> "save()...invoked");
		log.debug(() -> "subscription: " + subscription);

		// (2) often not doing domain validation

		// (3) not hanlding exception
		return subscriptionRepository.save(subscription);
	}

}
