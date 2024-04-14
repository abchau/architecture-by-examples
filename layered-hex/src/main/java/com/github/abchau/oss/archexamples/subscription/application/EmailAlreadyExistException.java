package com.github.abchau.oss.archexamples.subscription.application;

public final class EmailAlreadyExistException extends RuntimeException {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
