package com.abchau.archexamples.subscribe.domain.model.subscription;

public class CannotCreateSubscriptionException extends Exception {
	
	public static final String CODE = "error.create";

	public CannotCreateSubscriptionException(String msg) {
		super(msg);
	}

}
