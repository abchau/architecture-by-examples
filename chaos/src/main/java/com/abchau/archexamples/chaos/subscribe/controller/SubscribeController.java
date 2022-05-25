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

	// (3) redundant api
	private SubscriptionRepository subscriptionRepository;

	// (4) every use cases, internal or external logic all in one single service
	private SubscriptionService subscriptionService;

	@Autowired
	public SubscribeController(
		SubscriptionRepository subscriptionRepository, 
		SubscriptionService subscriptionService
	) {
		this.subscriptionRepository = subscriptionRepository;
		this.subscriptionService = subscriptionService;
	}

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
		params.put("email", List.of(subscriptionService.sanitizeInput(params.getFirst("email"))));

		// (1) dafuq
		// (12) not parameterized
		Map responseMap = new HashMap<>();
		
		// (8) no validation
		// (9) arrow code
		if (params.getFirst("email") != null && !"".equalsIgnoreCase(params.getFirst("email"))) {

			// (7) everything is a map
			// (10) bad variable scope
			Subscription subscription = subscriptionRepository.findByEmail(subscriptionService.sanitizeInput(params.getFirst("email")));
			// (1) dafuq
			responseMap.put("exist", subscription == null ? false : true);

			// (1) dafuq
			// (7) everything is a map
			// (9) arrow code
			if ((Boolean) responseMap.get("exist")) {
				// (1) dafuq
				responseMap.put("email", params.getFirst("email"));
				responseMap.put("message", "email.duplicate");
			} else {
				// (1) dafuq
				params.add("status", "COMPLETED");

				// (7) everything is a map
				// (10) bad variable scope
				// (8) no validation
				// (11) not catching exception
				subscription = subscriptionService.save(params);
				// (1) dafuq
				responseMap.put("subscription", subscription);

				if (subscription != null) {
					// (1) dafuq
					responseMap.put("message", "success");
				} else {
					// (1) dafuq
					responseMap.put("message", "email.format");
				}
			}
		} else {
			// (1) dafuq
			responseMap.put("message", "email.empty");
		}

		ModelAndView modelAndView = new ModelAndView("subscribe");
		// (1) dafuq
		modelAndView.addObject("email", responseMap.get("email"));
		// (1) dafuq
		modelAndView.addObject("subscription", responseMap.get("subscription"));
		// (1) dafuq
		modelAndView.addObject("message", responseMap.get("message"));

		return modelAndView;
	}

}
