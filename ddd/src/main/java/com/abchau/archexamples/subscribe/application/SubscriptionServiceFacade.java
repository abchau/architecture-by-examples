package com.abchau.archexamples.subscribe.application;

import java.util.Optional;

import com.abchau.archexamples.subscribe.application.dto.SubscriptionDto;

import lombok.Data;

public interface SubscriptionServiceFacade {

	public Optional<SubscriptionDto> createSubscription(CreateSubscriptionCommand createSubscriptionCommand) throws IllegalArgumentException, Exception;

	@Data
	public static class CreateSubscriptionCommand {
		String email;
	}

}
