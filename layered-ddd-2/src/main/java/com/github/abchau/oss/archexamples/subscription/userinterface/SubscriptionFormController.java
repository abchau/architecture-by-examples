package com.github.abchau.oss.archexamples.subscription.userinterface;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.abchau.oss.archexamples.subscription.application.SubscriptionFacade;
import com.github.abchau.oss.archexamples.subscription.application.SubscriptionFacade.CreateSubscriptionCommand;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;
import com.github.abchau.oss.archexamples.subscription.domain.SubscriptionException;

@Slf4j
@Controller
final class SubscribeRestController {

	private SubscriptionFacade subscriptionFacade;

	public SubscribeRestController(SubscriptionFacade subscriptionFacade) {
		this.subscriptionFacade = subscriptionFacade;
	}

    @GetMapping(value = "/")
	public String home() {
		return "redirect:/create";
	}

    @GetMapping(value = "/create")
	public ModelAndView showSubscribeForm() {
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

    @PostMapping(value = "/create")
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
		} catch (SubscriptionException e) {
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
