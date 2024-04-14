package com.github.abchau.oss.archexamples.subscription.application;

public final class EmailFormatException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
