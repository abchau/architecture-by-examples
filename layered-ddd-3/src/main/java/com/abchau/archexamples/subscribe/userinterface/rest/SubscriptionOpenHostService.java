package com.abchau.archexamples.subscribe.userinterface.rest;

import lombok.extern.log4j.Log4j2;

import com.abchau.archexamples.subscribe.application.SubscriptionFacade;
import com.abchau.archexamples.subscribe.application.SubscriptionFacade.CreateSubscriptionCommand;
import com.abchau.archexamples.subscribe.domain.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
final class SubscriptionOpenHostService {

	private SubscriptionFacade subscriptionFacade;

	@Autowired
	public SubscriptionOpenHostService(SubscriptionFacade subscriptionFacade) {
		this.subscriptionFacade = subscriptionFacade;
	}

    @PostMapping(value = "/api/v1/subscribe")
	public ResponseEntity<?> create(SubscriptionRequest subscriptionRequest) {
		try {
			if (subscriptionRequest == null || subscriptionRequest.email() == null || subscriptionRequest.email().trim() == "") {
				return new ResponseEntity<>("email.empty", HttpStatus.BAD_REQUEST);
			}

			CreateSubscriptionCommand createSubscriptionCommand = CreateSubscriptionCommand.of(subscriptionRequest.email());
			Subscription subscription = subscriptionFacade.createSubscription(createSubscriptionCommand);

			return new ResponseEntity<>(subscription, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			log.error("Known application error. ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Unknown application error. ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
