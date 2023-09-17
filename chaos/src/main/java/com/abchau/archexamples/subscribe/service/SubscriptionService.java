package com.abchau.archexamples.subscribe.service;

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
public class SubscriptionService extends GeneralService {

	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public SubscriptionService() {
	}

	@Transactional
    public Subscription findByEmail(Map params) {
		return subscriptionRepository.findByEmail((String) params.get("email"));
	}
	
	@Transactional
	public Map save(Map<String, String> params) {
		Map subscriptionMap = new HashMap<>();

		subscriptionMap.put("email", getValue(params, "email"));

		if (subscriptionMap.get("email") == null) {
			subscriptionMap.put("error", "empty");
		}

		Subscription subscription = null;

		if (subscriptionMap.get("error") == null) {
			subscription = subscriptionRepository.findByEmail(subscriptionMap.get("email").toString());
		}

		if (subscription == null) {
			subscriptionMap.put("error", "duplicate");
		}

		if (!commonUtils.isEmailValid(params.get("email"))) {
			subscriptionMap.put("error", "format");
		}

		if (subscriptionMap.get("error") == null) {
			subscription.setEmail(subscriptionMap.get("email").toString());
			subscription.setStatus(getValue(params, "status"));
			subscription.setCreatedAt(commonUtils.getCurrentTime());
			subscription = subscriptionRepository.save(subscription);
			subscriptionMap.put("subscription", subscription);
		}

		return subscriptionMap;
	}

}
