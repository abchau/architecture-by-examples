package com.abchau.archexamples.subscribe.domain;

import java.time.Clock;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public final class Subscription {

	private Long id;
	
	private EmailAddress emailAddress;
	
	private String status;

	private ZonedDateTime createdAt;

	private Long version;

	public static Subscription of(EmailAddress emailAddress) throws EmailFormatException, InvalidSubscriptionStatusException {
		ZonedDateTime now = ZonedDateTime.now(Clock.systemDefaultZone());

		Subscription subscription = Subscription.builder()
			.emailAddress(emailAddress)
			.status("PENDING")
			.createdAt(now)
			.build();

		if (subscription.getEmailAddress().isStatusValidFormat()) {
			subscription.toValidated();
		} else {
			throw new EmailFormatException("email.format");
		}

		return subscription;
	}

	public void toValidated() throws InvalidSubscriptionStatusException{
		if (!status.equalsIgnoreCase("PENDING")) {
			throw new InvalidSubscriptionStatusException("error.status.invalid");
		}

		this.setStatus("VALIDATED");
	}

	public void toConfirmed() throws InvalidSubscriptionStatusException {
		if (!status.equalsIgnoreCase("VALIDATED")) {
			throw new InvalidSubscriptionStatusException("error.status.invalid");
		}

		this.setStatus("CONFIRMED");
	}

}
