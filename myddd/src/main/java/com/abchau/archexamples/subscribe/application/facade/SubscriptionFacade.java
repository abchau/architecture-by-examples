package com.abchau.archexamples.subscribe.application.facade;

import java.util.Optional;

import com.abchau.archexamples.subscribe.application.dto.SubscriptionDto;

import lombok.Data;
public interface SubscriptionFacade {

	public Optional<SubscriptionDto> createSubscription(CreateSubscriptionCommand subscriptionCommand) throws IllegalArgumentException, Exception;

	@Data
	public static class CreateSubscriptionCommand {
		String email;
	}

}
