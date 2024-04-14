package com.github.abchau.oss.archexamples.subscription.application;

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
			.status("NEW")
			.createdAt(createdAt)
			.build();
	}

}
