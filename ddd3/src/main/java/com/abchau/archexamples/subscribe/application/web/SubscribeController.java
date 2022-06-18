package com.abchau.archexamples.subscribe.application.web;

import com.abchau.archexamples.subscribe.domain.model.subscription.CannotCreateSubscriptionException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAddress;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.model.subscription.EmailIsEmptyException;
import com.abchau.archexamples.subscribe.domain.model.subscription.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.model.subscription.Subscription;
import com.abchau.archexamples.subscribe.domain.service.SubscriptionService;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class SubscribeController {

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscribeController(final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

    @GetMapping(value = "/subscribe")
	public ModelAndView showSubscribeForm() {
		log.trace("showSubscribeForm()...invoked");

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

	// (1) use a command
    @PostMapping(value = "/subscribe")
	public ModelAndView processSubscribeForm(CreateSubscriptionCommand createSubscriptionCommand) {
		log.trace("processSubscribeForm()...invoked");
		log.debug("createSubscriptionCommand: {}", () -> createSubscriptionCommand);

		ModelAndView modelAndView = new ModelAndView("subscribe");
		
		// (1) tasks coordination and wrap tasks in a transaction
		// (2) catch application error and business error
		try {
			// (3) pass a command in application layer, not domain object in domain model layer

			String email = createSubscriptionCommand.getEmail();
			log.debug("email: {}", () -> email);
	
			// (3) do input validation in application layer
			if (email == null || "".equalsIgnoreCase(email)) {
				throw new EmailIsEmptyException("email.empty");
			}
	
			Subscription newSubscription = Subscription.of(EmailAddress.of(email), ZonedDateTime.now(Clock.systemDefaultZone()));
			log.debug("newSubscription: {}", () -> newSubscription);
	
			// (3) do input validation in application layer
			if (!newSubscription.getEmailAddress().isValidFormat()) {
				throw new EmailFormatException("email.format");
			}
	
			// (4) validate state before changing
			if (newSubscription.isValidForValidated()) {
				newSubscription.toValidated();
			}
	
			Subscription savedSubscription = subscriptionService.createSubscription(newSubscription);
			log.debug("savedSubscription: {}", () -> savedSubscription);
			
			// (5) Anti-corruption layer (ACL)
			SubscriptionDto subscriptionDto = AntiCorruptionLayer.translate(savedSubscription);
			log.debug("subscriptionDto: {}", () -> subscriptionDto);

			modelAndView.addObject("email", subscriptionDto.getEmail());
			modelAndView.addObject("message", "success");
		}
		// (6) demonstrate translating domain exception
		catch (EmailIsEmptyException | EmailFormatException | EmailAlreadyExistException | InvalidSubscriptionStatusException e) {
			log.error("known domain error. ", e);
			modelAndView.addObject("email", createSubscriptionCommand.getEmail());
			modelAndView.addObject("message", e.getMessage());
		} 
		// (6) demonstrate translating domain exception
		catch (CannotCreateSubscriptionException e) {
			log.error("Unknown domain error. ", e);
			modelAndView.addObject("message", "error.unknown");
		} 
		// (6) demonstrate translating domain exception
		catch (Exception e) {
			log.error("Unknown domain error. ", e);
			modelAndView.addObject("message", "error.unknown");
		}

		return modelAndView;
	}

	// (6) Anti-corruption layer (ACL)
	private static class AntiCorruptionLayer {

		public static SubscriptionDto translate(Subscription subscription) {
			log.debug("subscription: {}", () -> subscription);
			Objects.requireNonNull(subscription);

			return SubscriptionDto.builder()
				.id(subscription.getId().getValue())
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus().toString())
				.subscribedAt(subscription.getSubscribedAt())
				.build();
		}
	}

	@Data
	public static class CreateSubscriptionCommand {
		String email;
	}
	
}
