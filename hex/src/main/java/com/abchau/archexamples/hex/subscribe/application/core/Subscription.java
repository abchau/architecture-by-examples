package com.abchau.archexamples.hex.subscribe.application.core;

import java.time.Clock;
import java.time.ZonedDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
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
