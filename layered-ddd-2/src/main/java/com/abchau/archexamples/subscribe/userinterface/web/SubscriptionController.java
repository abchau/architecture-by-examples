package com.abchau.archexamples.subscribe.userinterface.web;

import lombok.extern.log4j.Log4j2;

import com.abchau.archexamples.subscribe.application.SubscriptionFacade;
import com.abchau.archexamples.subscribe.application.SubscriptionFacade.CreateSubscriptionCommand;
import com.abchau.archexamples.subscribe.domain.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import org.jmolecules.architecture.layered.ApplicationLayer;

@Log4j2
@Controller
final class SubscribeRestController {

	private SubscriptionFacade subscriptionFacade;

	@Autowired
	public SubscribeRestController(SubscriptionFacade subscriptionFacade) {
		this.subscriptionFacade = subscriptionFacade;
	}

    @GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

    @PostMapping(value = "/subscribe")
	public ModelAndView processSubscribeForm(CreateSubscriptionForm createSubscriptionForm) {
		ModelAndView modelAndView = new ModelAndView("subscribe");

		try {
			if (createSubscriptionForm == null || createSubscriptionForm.email() == null || createSubscriptionForm.email().trim() == "") {
				modelAndView.addObject("message", "email.empty");
			}

			CreateSubscriptionCommand createSubscriptionCommand = CreateSubscriptionCommand.of(createSubscriptionForm.email());
			Subscription subscription = subscriptionFacade.createSubscription(createSubscriptionCommand);

			modelAndView.addObject("email", subscription.getEmailAddress().getValue());
			modelAndView.addObject("message", "success");
		} catch (IllegalArgumentException e) {
			log.error("Known application error. ", e);
			modelAndView.addObject("email", createSubscriptionForm.email());
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("Unknown application error. ", e);
			modelAndView.addObject("message", "error.unknown");
		}

		return modelAndView;
	}
	
	private static record CreateSubscriptionForm(String email) {}

}
