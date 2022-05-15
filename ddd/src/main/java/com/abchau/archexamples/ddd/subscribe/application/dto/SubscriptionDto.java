package com.abchau.archexamples.ddd.subscribe.application.dto;

import java.time.Clock;
import java.time.ZonedDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Data;

@Data
@Getter
@EqualsAndHashCode
@ToString
public class SubscriptionDto {

	private Long id;
	
	private String email;
	
	private String status;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastUpdatedAt;

}
