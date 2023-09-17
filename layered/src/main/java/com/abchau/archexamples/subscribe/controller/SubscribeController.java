package com.abchau.archexamples.subscribe.controller;

import lombok.extern.log4j.Log4j2;

import com.abchau.archexamples.subscribe.model.Subscription;
import com.abchau.archexamples.subscribe.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
final class SubscribeController {

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscribeController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

	@PostMapping(value = "/subscribe")
	public ModelAndView processSubscribeForm(@RequestBody MultiValueMap<String, String> params) {
		ModelAndView modelAndView = new ModelAndView("subscribe");

		String email = params.getFirst("email");
		if (email == null || "".equalsIgnoreCase(email)) {
			modelAndView.addObject("message", "email.empty");

			return modelAndView;
		}

		try {
			Subscription savedSubscription = subscriptionService.save(email);
			modelAndView.addObject("email", savedSubscription.getEmail());
			modelAndView.addObject("message", "success");
		} catch (IllegalArgumentException e) {
			log.error("known error", e);
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("error", e);
			modelAndView.addObject("message", "error.unknown");
		}

		return modelAndView;
	}

}
