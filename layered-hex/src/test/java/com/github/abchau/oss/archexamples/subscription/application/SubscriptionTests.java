package com.github.abchau.oss.archexamples.subscription.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.github.abchau.oss.archexamples.subscription.application.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.application.EmailFormatException;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;

public class SubscriptionTests {
	
	@Test
	public void should_throw_emailformatexception() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abcabc.com");

		// when
		assertThrows(
			// then	
			EmailFormatException.class, 
			() -> Subscription.of(emailAddress, LocalDateTime.now()));
	}

	@Test
	public void should_create_subscription() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		// when
		Subscription actual = Subscription.of(emailAddress, LocalDateTime.now());
		
		// then 
		assertNotNull(actual, "must create");
	}

}
