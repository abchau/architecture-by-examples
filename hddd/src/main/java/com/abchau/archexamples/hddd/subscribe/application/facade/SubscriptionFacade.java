package com.abchau.archexamples.hddd.subscribe.application.facade;

import com.abchau.archexamples.hddd.subscribe.application.dto.SubscriptionDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public interface SubscriptionFacade {

	public SubscriptionDto createSubscription(CreateSubscriptionCommand subscriptionCommand) throws IllegalArgumentException, Exception;

	@Data
	@Getter
	@EqualsAndHashCode
	@ToString
	public static class CreateSubscriptionCommand {
		String email;
	}

}
