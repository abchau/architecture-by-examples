package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

public class InvalidSubscriptionStatusException extends Exception {
	
	public static final String CODE = "error.status.invalid";

	public InvalidSubscriptionStatusException(String msg) {
		super(msg);
	}

}
