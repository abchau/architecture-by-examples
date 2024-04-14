package com.github.abchau.oss.archexamples.subscription.domain;

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
	
	private Status status;

	private LocalDateTime createdAt;

	public static Subscription of(EmailAddress emailAddress, LocalDateTime createdAt) {
		return Subscription.builder()
			.emailAddress(emailAddress)
			.status(Status.NEW)
			.createdAt(createdAt)
			.build();
	}

	/**
	 * Represents the different statuses for a subscription.
	 */
	public enum Status {
		NEW
	}
}
