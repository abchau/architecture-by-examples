package com.abchau.archexamples.hdddeda.subscribe.application.web;

import java.util.stream.Collectors;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;

import lombok.Value;
import lombok.extern.log4j.Log4j2;

import org.springframework.util.MultiValueMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;


/**
 * @common-name DTO
 * @dddarch-name
 * @cleanarch-name Presenter
 */
@Log4j2
@Value(staticConstructor = "of")
final class SubscribeForm {

	private static final Validator VALIDATOR = new SubscribeFormValidator();

	private String email;

	/**
	 * TODO shit codes
	 */
	public static SubscribeForm fromMultiValueMap(final MultiValueMap<String, String> map) {
		log.trace(() -> "fromMultiValueMap()...invoked");

		return SubscribeForm.of(map.getFirst("email"));
	}

	/**
	 * TODO shit codes
	 */
	public static Subscription toSubscription(final SubscribeForm subscribeForm) {
		log.trace(() -> "toSubscription()...invoked");

		return Subscription.of(subscribeForm.email);
	}

	public static void validate(final SubscribeForm subscribeForm) {
		log.trace(() -> "validateSubscribeForm()...invoked");

		Errors errors = new BeanPropertyBindingResult(subscribeForm, "subscribeForm");
		VALIDATOR.validate(subscribeForm, errors);

		log.trace(() -> "errors: " + errors.hasErrors() + ", " + errors.toString());

		if (errors.hasErrors()) {
			// collect all error codes into single String, e.g. "email.empty,password.empty"
			final String errorCodes = errors.getAllErrors().stream()
				.map(ObjectError::getCode)
				.collect(Collectors.joining(","));

			log.trace(() -> "errorCodes: " + errorCodes);

			throw new ServerWebInputException(errorCodes);
		}
	}

	/**
	 * @see https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-fn-handler-validation
	 */
	private static final class SubscribeFormValidator implements Validator {

		/**
		 * This Validator validates only SubscribeForm instances
		 */
		@Override
		public boolean supports(Class<?> clazz) {
			log.trace(() -> "supports()...invoked");

			return SubscribeForm.class.equals(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			log.trace(() -> "validate()...invoked");

			ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");

			SubscribeForm request = (SubscribeForm) target;
			
			if (request.getEmail() != null && (request.getEmail().length() < 3 || request.getEmail().length() > 512)) {
				errors.rejectValue("email", "email.size");
			}
		}
		
	}

}
