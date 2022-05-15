package com.abchau.archexamples.ddd.subscribe.interfaces.web;

import com.abchau.archexamples.ddd.subscribe.application.SubscriptionServiceFacade;
import com.abchau.archexamples.ddd.subscribe.application.SubscriptionServiceFacade.CreateSubscriptionCommand;
import com.abchau.archexamples.ddd.subscribe.application.dto.SubscriptionDto;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class SubscribeController {

	private SubscriptionServiceFacade subscriptionServiceFacade;

	@Autowired
	public SubscribeController(SubscriptionServiceFacade subscriptionServiceFacade) {
		this.subscriptionServiceFacade = subscriptionServiceFacade;
	}

    @GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		log.trace(() -> "showSubscribeForm()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

    @PostMapping(value = "/subscribe")
	// (1) use a command
	public ModelAndView processSubscribeForm(@RequestBody CreateSubscriptionCommand createSubscriptionCommand) {
		log.trace(() -> "processSubscribeForm()...invoked");
		log.debug(() -> "createSubscriptionCommand: " + createSubscriptionCommand);

		ModelAndView modelAndView = new ModelAndView("subscribe");

		// (2) extract values from the request immediate
		String email = createSubscriptionCommand.getEmail();
		log.debug(() -> "email: " + email);

		// (3) also do validation here
		if (email == null || "".equalsIgnoreCase(email)) {
			modelAndView.addObject("message", "email.empty");

			return modelAndView;
		}

		// (5) catch application error and business error
		try {
			// (6) pass a command, not domain object
			SubscriptionDto savedSubscription = subscriptionServiceFacade.createSubscription(createSubscriptionCommand);
			log.debug(() -> "savedSubscription: " + savedSubscription);

			modelAndView.addObject("email", savedSubscription.getEmail());
			modelAndView.addObject("message", "success");
		} catch (IllegalArgumentException e) {
			log.error("Known error. ", e);
			modelAndView.addObject("email", email);
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("Unknown error. ", e);
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

}
