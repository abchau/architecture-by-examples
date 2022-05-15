package com.abchau.archexamples.hdddeda.subscribe.domain.entity;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 
 * @dddarch-name Aggregate Root, Entity
 * @hexarch-name Domain Object
 */
@Builder
@Data
public class Subscription {

	public static enum Status {
		REQUESTED, 
		CREATED, 
		DECLINED
	}

	private UUID id;
	
    @NotNull(message = "email.empty")
    @Size(min = 7, max = 512, message = "email.size")
	private EmailAddress emailAddress;
	
	private Status status; 

	private ZonedDateTime createdAt;
	
	private ZonedDateTime lastUpdatedAt;

	// never update this manually
	private Long version;

	public static Subscription of(final String email) {
		final ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());

		return Subscription.builder()
			.id(UUID.randomUUID())
			.emailAddress(EmailAddress.of(email))
			// .status(Status.REQUESTED)
			.createdAt(now)
			.lastUpdatedAt(now)
			.build();
	}

	public static boolean isValidForRequested(final Subscription subscription) {
		return subscription.getEmailAddress() != null 
			&& subscription.getEmailAddress().isValidFormat();
	}

	public Subscription requested() {
		this.status = Status.REQUESTED;
		this.lastUpdatedAt = ZonedDateTime.now(Clock.systemUTC());

		return this;
	}

	public static boolean isValidForCreated(final Subscription subscription) {
		return subscription.getEmailAddress() != null 
			&& subscription.getStatus() == Status.REQUESTED;
	}

	public Subscription created() {
		this.status = Status.CREATED;
		this.lastUpdatedAt = ZonedDateTime.now(Clock.systemUTC());

		return this;
	}

	public static boolean isValidForDeclined(final Subscription subscription) {
		return subscription.getEmailAddress() != null 
			&& subscription.getStatus() == Status.CREATED;
	}

	public Subscription declined() {
		this.status = Status.DECLINED;
		this.lastUpdatedAt = ZonedDateTime.now(Clock.systemUTC());

		return this;
	}

}
