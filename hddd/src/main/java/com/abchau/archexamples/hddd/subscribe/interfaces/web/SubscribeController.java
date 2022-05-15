package com.abchau.archexamples.hddd.subscribe.interfaces.web;

import com.abchau.archexamples.hddd.subscribe.application.facade.SubscriptionFacade;
import com.abchau.archexamples.hddd.subscribe.application.facade.SubscriptionFacade.CreateSubscriptionCommand;
import com.abchau.archexamples.hddd.subscribe.application.dto.SubscriptionDto;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class SubscribeController {

	private SubscriptionFacade subscriptionFacade;

	@Autowired
	public SubscribeController(SubscriptionFacade subscriptionFacade) {
		this.subscriptionFacade = subscriptionFacade;
	}

	// (4) bad method name
    @GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		log.trace(() -> "showSubscribeForm()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

    @PostMapping(value = "/subscribe")
	public ModelAndView processSubscribeForm(CreateSubscriptionCommand createSubscriptionCommand) {
		log.trace(() -> "processSubscribeForm()...invoked");
		log.debug(() -> "createSubscriptionCommand: " + createSubscriptionCommand);

		ModelAndView modelAndView = new ModelAndView("subscribe");

		// (4) catch application error and business error
		try {
			// (3) pass a command in application layer, not domain object in domain model layer
			SubscriptionDto subscriptionDto = subscriptionFacade.createSubscription(createSubscriptionCommand)
				.orElseThrow();
			log.debug(() -> "savedSubscription: " + subscriptionDto);

			modelAndView.addObject("email", subscriptionDto.getEmail());
			modelAndView.addObject("message", "success");
		} catch (IllegalArgumentException e) {
			log.error("Known error. ", e);
			modelAndView.addObject("email", createSubscriptionCommand.getEmail());
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("Unknown error. ", e);
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

}
