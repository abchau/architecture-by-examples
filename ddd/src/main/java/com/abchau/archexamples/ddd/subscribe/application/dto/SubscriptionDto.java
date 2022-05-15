package com.abchau.archexamples.ddd.subscribe.application.dto;

import java.time.Clock;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SubscriptionDto {

	private Long id;
	
	private String email;
	
	private String status;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastUpdatedAt;

}
