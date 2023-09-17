package com.abchau.archexamples.subscribe.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;

@AllArgsConstructor
@Builder
@Data
@AggregateRoot
@Entity
public final class Subscription {

	private SubscriptionId id;
	
	private EmailAddress emailAddress;
	
	private SubscriptionStatus status;

	private LocalDateTime subscribedAt;

	public static Subscription of(EmailAddress emailAddress, LocalDateTime subscribedAt) {
		return Subscription.builder()
			.emailAddress(emailAddress)
			.status(SubscriptionStatus.INITIALIED)
			.subscribedAt(subscribedAt)
			.build();
	}

}
