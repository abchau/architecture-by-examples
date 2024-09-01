package com.github.abchau.oss.archexamples.subscription.application;

public class SubscriptionException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public SubscriptionException(String msg) {
		super(msg);
	}

}
