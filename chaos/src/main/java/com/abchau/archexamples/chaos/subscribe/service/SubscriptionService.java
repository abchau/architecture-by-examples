package com.abchau.archexamples.chaos.subscribe.service;

import java.time.Clock;
import java.time.ZonedDateTime;

import com.abchau.archexamples.chaos.subscribe.model.Subscription;
import com.abchau.archexamples.chaos.subscribe.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	@Autowired
	public SubscriptionService(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	// (1) use write transation for readonly task
	@Transactional
    public Subscription findByEmail(final String email) {
		log.trace(() -> "findByEmail()...invoked");
		log.debug(() -> "email: " + email);

		return subscriptionRepository.findByEmail(email);
	}
	
	// (2) bad method name
	// (3) spaghetti code
	// (4) everything is a map
	// (5) web technology leak into business logic
	@Transactional
	public Subscription save(MultiValueMap<String, String> params) {
		log.trace(() -> "save()...invoked");
		log.debug(() -> "params: " + params);

		// (6) not using exception
		if (!Subscription.isEmailValid(params.getFirst("email"))) {
			return null;
		}

		// (7) everything is a map
		// (8) not using constructor nor factory
		Subscription subscription = new Subscription();
		subscription.setEmail(params.getFirst("email"));
		ZonedDateTime now = ZonedDateTime.now(Clock.systemDefaultZone());
		subscription.setStatus("COMPLETED");
		subscription.setCreatedAt(now);
		subscription.setLastUpdatedAt(now);

		// (9) not hanlding exception
		return subscriptionRepository.save(subscription);
	}

}
