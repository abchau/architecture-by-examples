package com.abchau.archexamples.hddd.subscribe.domain.entity;

import java.time.Clock;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Subscription {

	private Long id;
	
	private EmailAddress emailAddress;
	
	private String status;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastUpdatedAt;

	private Long version;

	public static Subscription of(EmailAddress emailAddress) {
		ZonedDateTime now = ZonedDateTime.now(Clock.systemDefaultZone());

		return Subscription.builder()
			.emailAddress(emailAddress)
			.status("COMPLETED")
			.createdAt(now)
			.lastUpdatedAt(now)
			.build();
	}

}
