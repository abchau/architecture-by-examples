package com.abchau.archexamples.threetier.subscribe.controller;

import com.abchau.archexamples.threetier.subscribe.model.Subscription;
import com.abchau.archexamples.threetier.subscribe.service.SubscriptionService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class SubscribeController {

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscribeController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	// (4) bad method name
    @GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

    @PostMapping(value = "/subscribe")
	public ModelAndView processSubscribeForm(@RequestBody MultiValueMap<String, String> params) {
		log.trace(() -> "update()...invoked");

		// (1) extract values from the request immediate
		String email = params.getFirst("email");
		log.debug(() -> "email: " + email);

		ModelAndView modelAndView = new ModelAndView("subscribe");

		// (2) return immediately
		if (email == null || "".equalsIgnoreCase(email)) {
			modelAndView.addObject("message", "email.empty");

			return modelAndView;
		}

		// (2) return immediately
		if (subscriptionService.isAlreadyExist(email)) {
			modelAndView.addObject("email", email);
			modelAndView.addObject("message", "email.duplicate");
			return modelAndView;
		} 

		// (3) use constructor or factory pattern
		Subscription newSubscription = Subscription.of(email);
		log.debug(() -> "newSubscription: " + newSubscription);

		// (4) catch application error and business error
		try {
			// (5) pass a business object, not technology specific object nor map
			Subscription savedSubscription = subscriptionService.save(newSubscription);
			log.debug(() -> "savedSubscription: " + savedSubscription);

			modelAndView.addObject("email", savedSubscription.getEmail());
			modelAndView.addObject("message", "success");
		} catch (IllegalArgumentException e) {
			log.error("Known error. ", e);
			modelAndView.addObject("message", "email.format");
		} catch (Exception e) {
			log.error("Unknown error. ", e);
			modelAndView.addObject("message", "error.unknown");
		}

		return modelAndView;
	}

}