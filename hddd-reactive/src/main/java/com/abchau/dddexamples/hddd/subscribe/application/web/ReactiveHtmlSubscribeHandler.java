package com.abchau.archexamples.hddd.subscribe.application.web;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hddd.subscribe.domain.input.SubscriptionService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;

import reactor.core.publisher.Mono;

/**
 * 
 * @common-layer Web MVC Layer
 * @common-name Controller e.g. SubscribeController
 * @dddarch-layer
 * @dddarch-name
 * @hexarch-layer Input Adapter/Driving Adapter/Primary Adapter
 * @hexarch-name Web Input Adapter e.g. WebHtmlSubscribeInputAdapter
 */
@Log4j2
@Component
public class ReactiveHtmlSubscribeHandler {

	private SubscriptionService subscriptionService;

	@Autowired
	public ReactiveHtmlSubscribeHandler(final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	/**
	 * Show Subscription form
	 * GET /subscribe
	 */
	public Mono<ServerResponse> showSubscribeForm(final ServerRequest request) {
		log.trace(() -> "showSubscribeForm()...invoked");

		// if variable is a Mono
		// IReactiveDataDriverContextVariable reactiveDataDrivenMode = new
		// ReactiveDataDriverContextVariable(all, 1,1);

		final Optional<String> errors = request.queryParam("errors");
		log.debug(() -> "errors: " + errors);

		if (errors.isPresent()) {
			return ServerResponse.ok()
					.contentType(MediaType.TEXT_HTML)
					.render("subscribe", Map.of("errors", errors.get().split(",")));
		} else {
			return ServerResponse.ok()
					.contentType(MediaType.TEXT_HTML)
					.render("subscribe");
		}
	}

	/**
	 * Process Subscription form
	 * POST /subscribe
	 */
	public Mono<ServerResponse> processSubscribeForm(final ServerRequest request) {
		log.trace(() -> "processSubscribeForm()...invoked");

		log.debug(() -> "request formData: " + request.formData());

		// PRG Pattern
		return request.formData()
				// map form data to DTO
				.map(SubscribeForm::fromMultiValueMap)
				// do Requset Validation
				.doOnNext(SubscribeForm::validate)
				// map DTO to Domain object
				.map(Translator::toDomain)
				// execute a Domain use case which is a persisting a Subscription
				.flatMap(subscriptionService::requestSubscription)
				// execute a Domain use case which is a persisting a Subscription
				.flatMap(subscriptionService::createSubscription)
				// if all of above success, redirect to SUCCESS page
				.flatMap(r -> ServerResponse.status(HttpStatus.SEE_OTHER)
						.location(URI.create("/subscribe/submitted"))
						.build())
				// if all of above fail, redirect back to the FORM with errors
				// handles request validation errors
				.onErrorResume(ServerWebInputException.class, e -> {
					log.warn("Custom Validator errors", e.getMessage());

					return ServerResponse.status(HttpStatus.SEE_OTHER)
							.location(URI.create("/subscribe?errors=" + e.getReason()))
							.build();
				})
				// handles any uncatch errors
				.onErrorResume(e -> {
					log.error("Unknown runtime errors", e);

					return ServerResponse.status(HttpStatus.SEE_OTHER)
							.location(URI.create("/subscribe?errors=unknown"))
							.build();
				});
	}

	/**
	 * Show success page
	 * GET /subscribe/submitted
	 */
	public Mono<ServerResponse> showSubscribeFormSubmitted(final ServerRequest request) {
		log.trace(() -> "showSubscribeFormSubmitted()...invoked");

		// literally do nothing

		return ServerResponse.ok()
				.contentType(MediaType.TEXT_HTML)
				.render("subscribe_submitted");
	}

	private static class Translator {

		public static Subscription toDomain(final SubscribeForm subscribeForm) {
			log.trace(() -> "toDomain()...invoked");

			return Subscription.of(subscribeForm.getEmail());
		}

	}
	
}
