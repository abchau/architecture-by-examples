package com.github.abchau.oss.archexamples.subscription.application.businesslogic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.abchau.oss.archexamples.subscription.application.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.application.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;
import com.github.abchau.oss.archexamples.subscription.application.businesslogic.CreateSubscriptionUseCase;
import com.github.abchau.oss.archexamples.subscription.application.drivingport.CreateSubscriptionUseCasePort;
import com.github.abchau.oss.archexamples.subscription.outputadapter.persistence.InMemorySubscriptionPersistenceAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		Subscription input = Subscription.of(emailAddress, LocalDateTime.now());

		// when
		Subscription actual = createSubscriptionUseCasePort.execute(input);

		// then 
		assertNotNull(actual.getId(), "must generated PK");
	}

	@Test
	public void should_throw_emailalreadyexistException() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");
		Subscription subscription = Subscription.of(emailAddress, LocalDateTime.now());
		
		// when
		assertThrows(
			// then
			EmailAlreadyExistException.class, 
			() -> createSubscriptionUseCasePort.execute(subscription));
	}

}
