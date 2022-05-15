package com.abchau.archexamples.ddd.subscribe.application;

import java.util.Optional;

import com.abchau.archexamples.ddd.subscribe.application.dto.SubscriptionDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public interface SubscriptionServiceFacade {

	public Optional<SubscriptionDto> createSubscription(CreateSubscriptionCommand createSubscriptionCommand) throws IllegalArgumentException, Exception;

	@Data
	@Getter
	@EqualsAndHashCode
	@ToString
	public static class CreateSubscriptionCommand {
		String email;
	}

}
