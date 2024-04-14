package com.github.abchau.oss.archexamples.subscription.userinterface;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abchau.oss.archexamples.subscription.application.SubscriptionService;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;

@Slf4j
@Controller
final class SubscriptionFormController {

	private SubscriptionService subscriptionService;

	public SubscriptionFormController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
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
			log.error("Unknown error", e);
			modelAndView.addObject("message", "error.unknown");
		}

		return modelAndView;
	}

}
