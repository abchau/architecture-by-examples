package com.abchau.archexamples.hddd.subscribe.spring.config;

import lombok.extern.log4j.Log4j2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RequestPredicates;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.abchau.archexamples.hddd.subscribe.application.web.ReactiveHtmlSubscribeHandler;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Log4j2
@Configuration
public class RouterConfiguration {

	/**
	 * Root route
	 */
	@Bean
	public RouterFunction<ServerResponse> rootRoute() {
		log.trace(() -> "rootRoute()...invoked");

		return RouterFunctions.route()
				.GET("/", serverRequest -> ok().bodyValue("ROOT ALIVE"))
				.build();
	}

	/**
	 * For hosting static files
	 */
	@Bean
	public RouterFunction<ServerResponse> resourcesRoutes() {
		log.trace(() -> "staticRoute()...invoked");
		
		return RouterFunctions.resources("/favicon.ico", new ClassPathResource("static/"))
			.and(RouterFunctions.resources("/images/**", new ClassPathResource("static/images/")))
			.and(RouterFunctions.resources("/styles/**", new ClassPathResource("static/styles/")))
			.and(RouterFunctions.resources("/download/**", new ClassPathResource("static/download/")))
			.and(RouterFunctions.resources("/temp/**", new ClassPathResource("static/temp/")));
	}

	/**
	 * HTML routes
	 */
	@Bean
	public RouterFunction<ServerResponse> htmlRoutes(ReactiveHtmlSubscribeHandler reactiveHtmlSubscribeHandler) {
		log.trace(() -> "htmlRoutes()...invoked");
		
		return RouterFunctions.route()
				.before(req -> {
					log.info("Found a route which matches " + req.uri().getPath());
					return req;
				})
				// Subscribe routes
				.GET("/subscribe", accept(MediaType.TEXT_HTML), reactiveHtmlSubscribeHandler::showSubscribeForm)
				.POST("/subscribe", accept(MediaType.APPLICATION_FORM_URLENCODED), reactiveHtmlSubscribeHandler::processSubscribeForm)
				.GET("/subscribe/submitted", accept(MediaType.TEXT_HTML), reactiveHtmlSubscribeHandler::showSubscribeFormSubmitted)
				.after((req, res) -> {
					if (res.statusCode() == HttpStatus.OK) {
						log.info("Finished processing request: " + req.uri().getPath());
					} else {
						log.info("There was an error while processing request: " + req.uri());
					}
					return res;
				})     
				//  custom exception handler
				.onError(
					Throwable.class, 
					(e, req) -> {
						log.error("in HTML custom exception handler ");
						
						return badRequest()
							.body(new Error(e.getMessage()), Error.class);
					} 
				)
				.build();
	}

	/**
	 * REST API routes
	 */
	@Bean
	public RouterFunction<ServerResponse> apiRoutes() {
		log.trace(() -> "apiRoutes()...invoked");
		// Some routes must support both GET&POST because some framework does not allow
		// GET/HEAD method to have body.

		return RouterFunctions.route()
				// TODO show api doc
				.GET("/api", serverRequest -> ok().bodyValue("API ROOT ALIVE"))
				.nest(path("/api/v1"), builder -> 
					// /api
					builder.GET("/1", serverRequest -> ok().bodyValue("/api/v1/1 ALIVE"))
						.GET("/2", serverRequest -> ok().bodyValue("/api2/v1/2 ALIVE"))
						// .POST("/api/v1/Subscriptions", accept(MediaType.APPLICATION_JSON), restSubscriptionHandler::createSubscription)
						// .POST("/api/v1/email-Verifications", accept(MediaType.APPLICATION_JSON), restVerificationHandler::resendVeificationEmail)
						// sequence matters, this should put lowest
						.GET(serverRequest -> ok().bodyValue("/api/v1 ALIVE"))
				)
				.nest(path("/api/v2"), builder -> 
					// /api
					builder.GET("/1", serverRequest -> ok().bodyValue("/api/v2/1 ALIVE"))
						.GET("/2", serverRequest -> ok().bodyValue("/api/v2/2 ALIVE"))
						// sequence matters, this should put lowest
						.GET(serverRequest -> ok().bodyValue("/api/v2 ALIVE"))
				)
				// custom errors by routes
				.onError(
					Throwable.class, 
					(e, req) -> {
						log.error("in REST custom exception handler ");
						return  badRequest()
						.body(new Error(e.getMessage()), Error.class);
					} 
				)
				.build();
	}
}
