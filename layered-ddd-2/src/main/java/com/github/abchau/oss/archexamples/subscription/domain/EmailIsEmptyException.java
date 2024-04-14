package com.github.abchau.oss.archexamples.subscription.domain;

public final class EmailIsEmptyException extends RuntimeException {
	
	public static final String CODE = "email.empty";

	public EmailIsEmptyException(String msg) {
		super(msg);
	}

}
