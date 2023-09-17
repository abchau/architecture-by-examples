package com.abchau.archexamples.subscribe.domain;

public final class EmailFormatException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
