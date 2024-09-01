package com.github.abchau.oss.archexamples.subscription.application;

public final class EmailAlreadyExistException extends SubscriptionException {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
