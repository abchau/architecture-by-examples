package com.abchau.archexamples.chaos.subscribe.controller;

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

// (1) a controller with thousands of lines
@Log4j2
@Controller
public class SubscribeController {

	// (2) redundant api. are you exposing service or data store? pick one
	private SubscriptionRepository subscriptionRepository;

	// (3) every use cases, internal or external logic all in one single service
	private SubscriptionService subscriptionService;

	@Autowired
	public SubscribeController(
		SubscriptionRepository subscriptionRepository, 
		SubscriptionService subscriptionService
	) {
		this.subscriptionRepository = subscriptionRepository;
		this.subscriptionService = subscriptionService;
	}

	// (4) bad method name
    @GetMapping(value = "/subscribe")
	public ModelAndView create() {
		log.trace(() -> "create()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}


	// (5) everything is a map
    @PostMapping(value = "/subscribe")
	public ModelAndView update(@RequestBody MultiValueMap<String, String> params) {
		log.trace(() -> "subscribe()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");

		// (6) no validation
		// (7) arrow code
		if (params.getFirst("email") != null && !"".equalsIgnoreCase(params.getFirst("email"))) {

			// (5) everything is a map
			// (8) bad scope
			Subscription subscription = subscriptionRepository.findByEmail(params.getFirst("email"));

			// (5) everything is a map
			// (7) arrow code
			if (subscription != null) {
				modelAndView.addObject("email", params.getFirst("email"));
				modelAndView.addObject("message", "email.duplicate");
			} else {
				// (5) everything is a map
				// (8) bad scope
				// (9) no validation
				// (10) not using exception
				subscription = subscriptionService.save(params);

				// (11) not using exception
				if (subscription != null) {
					modelAndView.addObject("subscription", subscription);
					modelAndView.addObject("message", "success");
				} else {
					modelAndView.addObject("message", "email.format");
				}
			}
		} else {
			modelAndView.addObject("message", "email.empty");
		}

		return modelAndView;
	}

}
