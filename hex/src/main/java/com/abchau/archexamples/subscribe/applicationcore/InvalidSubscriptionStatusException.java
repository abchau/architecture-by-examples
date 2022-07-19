package com.abchau.archexamples.subscribe.application.core;

public final class InvalidSubscriptionStatusException extends Exception {
	
	public static final String CODE = "error.status.invalid";

	public InvalidSubscriptionStatusException(String msg) {
		super(msg);
	}

}
