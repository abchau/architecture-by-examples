package com.abchau.archexamples.hex.subscribe.application.core;

import java.time.Clock;
import java.time.ZonedDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Data;

@Data
@Getter
@EqualsAndHashCode
@ToString
public class Subscription {

	private Long id;
	
	private EmailAddress email;
	
	private String status;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastUpdatedAt;

	private Long version;

	public Subscription() {
	}

	public static Subscription of(String email) {
		Subscription subscription = new Subscription();

		subscription.setEmail(EmailAddress.of(email));
		ZonedDateTime now = ZonedDateTime.now(Clock.systemDefaultZone());
		subscription.setStatus("COMPLETED");
		subscription.setCreatedAt(now);
		subscription.setLastUpdatedAt(now);

		return subscription;
	}

}
