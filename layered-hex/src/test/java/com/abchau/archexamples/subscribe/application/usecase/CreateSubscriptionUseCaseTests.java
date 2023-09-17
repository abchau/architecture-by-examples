package com.abchau.archexamples.subscribe.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abchau.archexamples.subscribe.application.drivingport.CreateSubscriptionUseCasePort;
import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.outputadapter.persistence.InMemorySubscriptionPersistenceAdapter;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Profile("test")
@Component
public class CreateSubscriptionUseCaseTests  {

	private CreateSubscriptionUseCasePort createSubscriptionUseCasePort;

	@BeforeEach
	public void setUp() {
		InMemorySubscriptionPersistenceAdapter outputAdapter = new InMemorySubscriptionPersistenceAdapter();
		createSubscriptionUseCasePort = new CreateSubscriptionUseCase(outputAdapter, outputAdapter);
	}

	@Test
	public void should_throw_nullpointerexception() {
		// given
		Subscription input = null;

		// when
		assertThrows(
			// then
			NullPointerException.class, 
			() -> createSubscriptionUseCasePort.execute(input));
	}

	@Test
	public void should_save_subscription() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		try {
			Subscription input = Subscription.of(emailAddress);

			// when
			Subscription actual = createSubscriptionUseCasePort.execute(input);

			// then 
			assertNotNull(actual.getId(), "must generated PK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void should_throw_invalidsubscriptionstatusexception() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		// when
		try {
			Subscription subscription = Subscription.of(emailAddress);
			subscription.setStatus("PENDING");

			assertThrows(
				// then
				InvalidSubscriptionStatusException.class, 
				() -> createSubscriptionUseCasePort.execute(subscription));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void should_throw_emailalreadyexistException() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		try {
			Subscription input1 = Subscription.of(emailAddress);
			createSubscriptionUseCasePort.execute(input1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// when
		try {
			Subscription subscription2 = Subscription.of(emailAddress);
			assertThrows(
				// then
				EmailAlreadyExistException.class, 
				() -> createSubscriptionUseCasePort.execute(subscription2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
