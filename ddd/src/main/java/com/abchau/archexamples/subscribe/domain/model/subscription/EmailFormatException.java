package com.abchau.archexamples.subscribe.domain.model.subscription;

public final class EmailFormatException extends Exception {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
