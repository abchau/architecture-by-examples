package com.abchau.archexamples.chaos.subscribe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abchau.archexamples.chaos.subscribe.model.Subscription;
import com.abchau.archexamples.chaos.subscribe.repository.SubscriptionRepository;
import com.abchau.archexamples.chaos.subscribe.service.SubscriptionService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

// (2) a controller with thousands of lines
@Log4j2
@Controller
public class SubscribeController {

	@Autowired
	// (3) redundant api
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	// (4) every use cases, internal or external logic all in one single service
	private SubscriptionService subscriptionService;

	// (11) Not using constructor injection
	public SubscribeController() {}

	// (5) bad method name
    @GetMapping(value = "/subscribe")
	public ModelAndView create() {
		log.trace(() -> "create()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

	// (6) bad method name
	// (7) everything is a map
    @PostMapping(value = "/subscribe")
	public ModelAndView update(@RequestBody MultiValueMap<String, String> params) {
		log.trace(() -> "subscribe()...invoked");

		// (1) dafuq
		// just demonstrate how it muddle origin request
		params.put("email", List.of(subscriptionService.sanitizeInput(params.getFirst("email"))));

		// (1) dafuq
		// (7) everything is a map
		Map subscriptionMap = new HashMap<>();
		
		// (8) no validation
		// (9) arrow code
		if (params.getFirst("email") != null && !"".equalsIgnoreCase(params.getFirst("email"))) {
			// (1) dafuq
			params.add("status", "COMPLETED");

			// (7) everything is a map
			// (10) bad variable scope
			// (8) no validation
			// (11) not catching exception
			 subscriptionMap = subscriptionService.save(params);

			// (1) dafuq
			// (7) everything is a map
			// (9) arrow code
			if (subscriptionMap.get("subscription") == null) {
				// (1) dafuq
				subscriptionMap.put("message", "email.empty");
			} else {
				if (subscriptionMap.get("error") != null) {
					subscriptionMap.put("message", subscriptionMap.get("error"));

					// (1) dafuq
					switch((String) subscriptionMap.get("error")) {
						case "empty":
						subscriptionMap.put("message", "email.empty");
						break;
						case "format":
						subscriptionMap.put("message", "email.format");
						break;
						case "duplicate":
						subscriptionMap.put("message", "email.duplicate");
						break;
						default:
					}
				} else {
					subscriptionMap.put("message", "success");
				}
			}
		} else {
			// (1) dafuq
			subscriptionMap.put("message", "email.empty");
		}

		// (1) dafuq
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addAllObjects(subscriptionMap);

		return modelAndView;
	}

}
