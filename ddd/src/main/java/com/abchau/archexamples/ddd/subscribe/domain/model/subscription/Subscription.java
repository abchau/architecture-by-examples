package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

import java.time.Clock;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	private EmailAddress emailAddress;
	
	private String status;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastUpdatedAt;

	private Long version;

	public Subscription() {
	}

	public static Subscription of(EmailAddress emailAddress) {
		Subscription subscription = new Subscription();

		subscription.setEmailAddress(emailAddress);
		ZonedDateTime now = ZonedDateTime.now(Clock.systemDefaultZone());
		subscription.setStatus("COMPLETED");
		subscription.setCreatedAt(now);
		subscription.setLastUpdatedAt(now);

		return subscription;
	}

}
