package com.github.abchau.oss.archexamples.subscription.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.model.Subscription;
import com.github.abchau.oss.archexamples.subscription.repository.SubscriptionDataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubscriptionUtil extends GeneralService {

	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private SubscriptionDataService subscriptionDataService;

	public SubscriptionUtil() {
	}

	@Transactional
    public Subscription findByEmail(Map params) {
		return subscriptionDataService.findByEmail((String) params.get("email"));
	}
	
	@Transactional
	public Map save(Map<String, String> params) {
		Map subscriptionMap = new HashMap<>();
		subscriptionMap.put("email", getValue(params, "email"));

		Subscription subscription = null;

		if (subscriptionMap.get("email") != null) { 
			subscription = subscriptionDataService.findByEmail(subscriptionMap.get("email").toString());
			
			if (subscription == null) {
				if (!commonUtils.isEmailValid(params.get("email"))) {
					subscriptionMap.put("error", "format");
				}
		
				if (subscriptionMap.get("error") == null) {
					subscription = new Subscription();
					subscription.setEmail(subscriptionMap.get("email").toString());
					subscription.setStatus(getValue(params, "status"));
					subscription.setCreatedAt(commonUtils.getCurrentTime());
					subscription = subscriptionDataService.save(subscription);
					subscriptionMap.put("subscription", subscription);
				}
			} else {
				subscriptionMap.put("error", "duplicate");
			}

		} else {
			subscriptionMap.put("error", "empty");
		}

		return subscriptionMap;
	}

}
