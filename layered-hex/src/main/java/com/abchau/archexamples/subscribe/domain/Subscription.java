package com.abchau.archexamples.subscribe.domain;

import java.time.LocalDateTime;

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

	private LocalDateTime createdAt;

	public static Subscription of(EmailAddress emailAddress, LocalDateTime createdAt) {
		return Subscription.builder()
			.emailAddress(emailAddress)
			.status("INITIALIZED")
			.createdAt(createdAt)
			.build();
	}

}
