package com.abchau.archexamples.hex.subscribe.inputadapter.web;

import lombok.extern.log4j.Log4j2;

import com.abchau.archexamples.hex.subscribe.application.core.EmailAddress;
import com.abchau.archexamples.hex.subscribe.application.core.EmailAlreadyExistException;
import com.abchau.archexamples.hex.subscribe.application.core.EmailFormatException;
import com.abchau.archexamples.hex.subscribe.application.core.Subscription;
import com.abchau.archexamples.hex.subscribe.application.core.SubscriptionFacade;

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

		// (1) extract values from the request immediate
		String email = params.getFirst("email");
		log.debug(() -> "email: " + email);

		ModelAndView modelAndView = new ModelAndView("subscribe");

		// (2) return immediately
		if (email == null || "".equalsIgnoreCase(email)) {
			modelAndView.addObject("message", "email.empty");

			return modelAndView;
		}

		// (3) catch application error and business error
		try {
			// (4) pass a business object, not technology specific object nor map
			Subscription savedSubscription = subscriptionFacade.createSubscription(email);
			log.debug(() -> "savedSubscription: " + savedSubscription);

			modelAndView.addObject("email", savedSubscription.getEmail());
			modelAndView.addObject("message", "success");
		} catch (EmailFormatException e) {
			log.error("Known error. ", e);
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("Unknown error. ", e);
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

}
