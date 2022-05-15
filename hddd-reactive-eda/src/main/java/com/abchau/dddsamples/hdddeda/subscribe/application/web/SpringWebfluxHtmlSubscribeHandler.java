package com.abchau.archexamples.hdddeda.subscribe.application.web;

import java.net.URI;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hdddeda.subscribe.domain.input.SubscriptionService;

import lombok.Data;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @arch Event Driven Architecture
 */
@Log4j2
@Component
public class SpringWebfluxHtmlSubscribeHandler {

	private SubscriptionService SubscriptionService;

	@Autowired
	public SpringWebfluxHtmlSubscribeHandler(final SubscriptionService SubscriptionService) {
		this.SubscriptionService = SubscriptionService;
	}

	// GET /subscribe"
	public Mono<ServerResponse> showsubscribeForm(ServerRequest request) {
		log.trace(() -> "showsubscribeForm()...invoked");

		return ServerResponse.ok()
				.contentType(MediaType.TEXT_HTML)
				.render("subscribe");
	}

	// POST /subscribe
	public Mono<ServerResponse> processsubscribeForm(ServerRequest request) {
		log.trace(() -> "processsubscribeForm()...invoked");

		return request.formData()
				// map HTML form to DTO
				.map(SubscribeForm::fromMultiValueMap)
				// do input validation
				.doOnNext(SubscribeForm::validate) 
				// map DTO to Subscription Domain object
				.map(SubscribeForm::toSubscription)
				.map(SubscriptionService::requestSubscription)
				.flatMap(e -> ServerResponse.temporaryRedirect(URI.create("/subscribe/submitted"))
						.build())
				.onErrorResume(e -> ServerResponse.temporaryRedirect(URI.create("/subscribe?error=" + e.getMessage()))
						.build());
	}

	// GET /subscribe/submitted
	public Mono<ServerResponse> showsubscribeFormSubmitted(ServerRequest request) {
		log.trace(() -> "showsubscribeFormSubmitted()...invoked");

		return ServerResponse.ok()
				.contentType(MediaType.TEXT_HTML)
				.render("subscribe_submitted");
	}

}
