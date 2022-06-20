package com.abchau.archexamples.subscribe.domain.model.subscription;

public final class InvalidSubscriptionStatusException extends Exception {
	
	public static final String CODE = "error.status.invalid";

	public InvalidSubscriptionStatusException(String msg) {
		super(msg);
	}

}
