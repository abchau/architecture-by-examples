package com.abchau.archexamples.hddd.subscribe.application.dto;

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
