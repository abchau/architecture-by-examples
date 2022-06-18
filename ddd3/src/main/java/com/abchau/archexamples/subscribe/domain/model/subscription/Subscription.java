package com.abchau.archexamples.subscribe.domain.model.subscription;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public final class Subscription {

	// (1) use value object
	private SubscriptionId id;
	
	// (1) use value object
	private EmailAddress emailAddress;
	
	// (1) use value object
	private SubscriptionStatus status;

	// (1) better name
	private ZonedDateTime subscribedAt;
	
	public static Subscription of(EmailAddress emailAddress, ZonedDateTime now) {
		return Subscription.builder()
			.emailAddress(emailAddress)
			.status(SubscriptionStatus.PENDING)
			.subscribedAt(now)
			.build();
	}

	public boolean isValidForValidated() throws InvalidSubscriptionStatusException {
		if (this.status != SubscriptionStatus.PENDING) {
			throw new InvalidSubscriptionStatusException("error.status.invalid");
		}

		return true;
	}
	
	public void toValidated() {
		this.status = SubscriptionStatus.VALIDATED;
	}

	public boolean isStatusValidated() {
		return this.status == SubscriptionStatus.VALIDATED;
	}

	public boolean isValidForConfirmed() throws InvalidSubscriptionStatusException {
		if (this.status != SubscriptionStatus.VALIDATED) {
			throw new InvalidSubscriptionStatusException("error.status.invalid");
		}

		return true;
	}

	public void toConfirmed() {
		this.status = SubscriptionStatus.CONFIRMED;
	}

	public boolean isStatusConfirmed() {
		return this.status == SubscriptionStatus.CONFIRMED;
	}
}
