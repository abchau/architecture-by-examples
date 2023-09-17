package com.abchau.archexamples.subscribe.outputadapter.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.Subscription;

import lombok.extern.log4j.Log4j2;

@Log4j2
@DataJpaTest
public class SubscriptionJpaPersistenceAdapterIT {
	
	@Autowired
	private SubscriptionJpaRepository subscriptionJpaRepository;

	private SubscriptionJpaPersistenceAdapter subscriptionJpaPersistenceAdapter;

	@BeforeEach
	public void setUp() {
		subscriptionJpaPersistenceAdapter = new SubscriptionJpaPersistenceAdapter(subscriptionJpaRepository);
	}

	@Test
	public void should_save_subscription() {
		//given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		try {
			Subscription subscription = Subscription.of(emailAddress);

			// when
			Subscription actual = subscriptionJpaPersistenceAdapter.save(subscription);

			// then
			assertNotNull(actual.getId(), "PK ahould be genereated");
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	@Test
	public void should_count_zero() {
		//given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		// when
		int actual = subscriptionJpaPersistenceAdapter.countByEmail(emailAddress);

		// then
		assertEquals(0, actual);
	}

	@Test
	public void should_count_one() {
		//given
		EmailAddress emailAddress = EmailAddress.of("abc@abc.com");

		try {
			Subscription subscription = Subscription.of(emailAddress);
			subscriptionJpaPersistenceAdapter.save(subscription);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		// when
		int actual = subscriptionJpaPersistenceAdapter.countByEmail(emailAddress);

		// then
		assertEquals(1, actual);
	}

}
