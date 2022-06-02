package com.abchau.archexamples.subscribe.service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.abchau.archexamples.subscribe.model.Subscription;
import com.abchau.archexamples.subscribe.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SubscriptionService extends /* (1) dafuq */ GeneralService {

	@Autowired
	// (1) dafuq
	private CommonUtils commonUtils;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	// (11) Not using constructor injection
	public SubscriptionService() {
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
	public Map save(MultiValueMap<String, String> params) {
		log.trace(() -> "save()...invoked");
		log.debug(() -> "params: " + params);

		// (1) dafuq
		// (5) everything is a map
		// (12) not parameterized
		Map subscriptionMap = new HashMap<>();

		// (1) dafuq
		// (5) everything is a map
		subscriptionMap.put("email", params.getFirst("email"));

		// (1) dafuq
		if (params.getFirst("email") == null) {
			// (1) dafuq
			subscriptionMap.put("error", "empty");

			return subscriptionMap;
		}

		// (10) bad variable scope
		Subscription subscription;

		// (7) everything is a map
		// (10) bad variable scope
		subscription = subscriptionRepository.findByEmail(params.getFirst("email"));

		// (1) dafuq
		if (subscription == null) {
			// (1) dafuq
			// (5) everything is a map
			subscriptionMap.put("error", "duplicate");

			return subscriptionMap;
		}

		// (7) not using constructor nor factory

		// (1) dafuq
		// (5) everything is a map
		if (!subscription.isEmailValid(params.getFirst("email"))) {
			// (8) not using exception
			// (1) dafuq
			// (5) everything is a map
			subscriptionMap.put("error", "format");

			return subscriptionMap;
		}

		// (1) dafuq
		subscription.setEmail(params.getFirst("email"));
		// (1) dafuq
		subscription.setStatus(params.getFirst("status"));
		// (1) dafuq
		subscription.setCreatedAt(commonUtils.getCurrentTime());

		// (9) not hanlding exception
		// (10) bad variable scope
		subscription = subscriptionRepository.save(subscription);

		// (1) dafuq
		// (5) everything is a map
		subscriptionMap.put("subscription", subscription);

		return subscriptionMap;
	}

}
