package com.github.abchau.oss.archexamples.subscription.drivingadapter.web;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abchau.oss.archexamples.subscription.application.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.application.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.application.EmailFormatException;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;
import com.github.abchau.oss.archexamples.subscription.application.drivingport.CreateSubscriptionUseCasePort;

@PrimaryAdapter
@Slf4j
@Controller
class SubscriptionController {

	private CreateSubscriptionUseCasePort createSubscriptionUseCasePort;

	public SubscriptionController(CreateSubscriptionUseCasePort createSubscriptionUseCasePort) {
		this.createSubscriptionUseCasePort = createSubscriptionUseCasePort;
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
			modelAndView.setStatus(HttpStatus.BAD_REQUEST);

			return modelAndView;
		}

		try {
			Subscription subscription = Subscription.of(EmailAddress.of(email), LocalDateTime.now());
			Subscription savedSubscription = createSubscriptionUseCasePort.execute(subscription);

			modelAndView.setStatus(HttpStatus.CREATED);
			modelAndView.addObject("email", savedSubscription.getEmailAddress().getValue());
			modelAndView.addObject("message", "success");
		} catch (EmailFormatException | EmailAlreadyExistException e) {
			log.error("Known error. ", e);
			modelAndView.setStatus(HttpStatus.BAD_REQUEST);
			modelAndView.addObject("email", email);
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			log.error("Unknown error. ", e);
			modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			modelAndView.addObject("email", email);
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

}
