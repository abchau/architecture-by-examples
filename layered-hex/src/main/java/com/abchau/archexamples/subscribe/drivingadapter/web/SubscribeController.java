package com.abchau.archexamples.subscribe.drivingadapter.web;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.abchau.archexamples.subscribe.application.drivingport.CreateSubscriptionUseCasePort;
import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.Subscription;

@PrimaryAdapter
@Log4j2
@Controller
/*final*/ class SubscribeController {

	private CreateSubscriptionUseCasePort createSubscriptionUseCasePort;

	@Autowired
	public SubscribeController(CreateSubscriptionUseCasePort createSubscriptionUseCasePort) {
		this.createSubscriptionUseCasePort = createSubscriptionUseCasePort;
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
