package com.abchau.archexamples.chaos.subscribe.service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Map;

import com.abchau.archexamples.chaos.subscribe.model.Subscription;
import com.abchau.archexamples.chaos.subscribe.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionService extends GeneralService { // (1) dafuq

	// (1) dafuq
	@Autowired
	private CommonUtils commonUtils;

	private SubscriptionRepository subscriptionRepository;

	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	// (2) use write transation for readonly task
	@Transactional
    public Subscription findByEmail(Map params) {
		log.trace(() -> "findByEmail()...invoked");
		log.debug(() -> "email: " + params.get("email"));

		return subscriptionRepository.findByEmail((String) params.get("email"));
	}
	
	// (3) bad method name
	// (4) spaghetti code
	// (5) everything is a map
	// (6) web technology leak into business logic
	@Transactional
	public Subscription save(MultiValueMap<String, String> params) {
		log.trace(() -> "save()...invoked");
		log.debug(() -> "params: " + params);

		// (7) not using constructor nor factory
		Subscription subscription = new Subscription();

		// (1) dafuq
		// (5) everything is a map
		if (!subscription.isEmailValid(params.getFirst("email"))) {
			// (8) not using exception
			return null;
		}

		// (5) everything is a map
		subscription.setEmail(params.getFirst("email"));
		// (1) dafuq
		subscription.setStatus(params.getFirst("status"));
		// (1) dafuq
		subscription.setCreatedAt(commonUtils.getCurrentTime());
		// (1) dafuq
		subscription.setLastUpdatedAt(commonUtils.getCurrentTime());

		// (9) not hanlding exception
		return subscriptionRepository.save(subscription);
	}

}
