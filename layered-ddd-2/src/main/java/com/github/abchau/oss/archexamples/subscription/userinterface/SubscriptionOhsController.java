package com.github.abchau.oss.archexamples.subscription.userinterface;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.abchau.oss.archexamples.subscription.application.SubscriptionFacade;
import com.github.abchau.oss.archexamples.subscription.application.SubscriptionFacade.CreateSubscriptionCommand;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;

@Slf4j
@Controller
final class SubscriptionOhsController {

	private SubscriptionFacade subscriptionFacade;

	public SubscriptionOhsController(SubscriptionFacade subscriptionFacade) {
		this.subscriptionFacade = subscriptionFacade;
	}

    @PostMapping(value = "/ohs/v1/create")
	public ResponseEntity<?> create(SubscriptionRequest subscriptionRequest) {
		try {
			if (subscriptionRequest == null || subscriptionRequest.email() == null || subscriptionRequest.email().trim() == "") {
				return new ResponseEntity<>("email.empty", HttpStatus.BAD_REQUEST);
			}

			CreateSubscriptionCommand createSubscriptionCommand = CreateSubscriptionCommand.of(subscriptionRequest.email());
			Subscription subscription = subscriptionFacade.createSubscription(createSubscriptionCommand);
			SubscriptionDto subscriptionDto = SubscriptionDto.builder()
				.email(subscription.getEmailAddress().getValue())
				.status(subscription.getStatus().toString())
				.build();

			return new ResponseEntity<>(subscriptionDto, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			log.error("Known application error. ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Unknown application error. ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Builder
	record SubscriptionDto(String email, String status) {}
	
	@Builder
	record SubscriptionRequest(String email) {}
	
}
