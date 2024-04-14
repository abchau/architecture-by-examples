package com.github.abchau.oss.archexamples.subscription.outputadapter.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.github.abchau.oss.archexamples.subscription.domain.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.domain.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
