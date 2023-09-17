package com.abchau.archexamples.subscribe.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.InvalidSubscriptionStatusException;
import com.abchau.archexamples.subscribe.domain.Subscription;

public class SubscriptionTests {
	
	@Test
	public void should_throw_emailformatexception() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abcabc.com");

		// when
		// then
		assertThrows(EmailFormatException.class, () -> Subscription.of(emailAddress));
	}

	@Test
	public void should_create_subscription() {
		// given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		try {
			// when
			Subscription actual = Subscription.of(emailAddress);
			
			// then 
			assertNotNull(actual, "must create");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
