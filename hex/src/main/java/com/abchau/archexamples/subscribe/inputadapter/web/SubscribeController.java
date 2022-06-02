package com.abchau.archexamples.subscribe.inputadapter.web;

import lombok.extern.log4j.Log4j2;

import com.abchau.archexamples.subscribe.application.SubscriptionFacade;
import com.abchau.archexamples.subscribe.application.core.Subscription;

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

	private SubscriptionFacade subscriptionFacade;

	@Autowired
	public SubscribeController(SubscriptionFacade subscriptionFacade) {
		this.subscriptionFacade = subscriptionFacade;
	}

    @GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		log.trace(() -> "showSubscribeForm()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

    @PostMapping(value = "/subscribe")
	public ModelAndView processSubscribeForm(@RequestBody MultiValueMap<String, String> params) {
		log.trace(() -> "processSubscribeForm()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");

		String email = params.getFirst("email");
		log.debug(() -> "email: " + email);

		// (1) do validation in adapter
		if (email == null || "".equalsIgnoreCase(email)) {
			modelAndView.addObject("message", "email.empty");

			return modelAndView;
		}

		// (2) catch application error and domain error
		try {
			Subscription savedSubscription = subscriptionFacade.createSubscription(email);
			log.debug(() -> "savedSubscription: " + savedSubscription);

			modelAndView.addObject("email", savedSubscription.getEmailAddress().getValue());
			modelAndView.addObject("message", "success");
		} catch (IllegalArgumentException e) {
			log.error("Known error. ", e);
			modelAndView.addObject("email", email);
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("Unknown error. ", e);
			modelAndView.addObject("email", email);
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

}
