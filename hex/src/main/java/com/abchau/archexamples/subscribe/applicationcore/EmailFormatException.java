package com.abchau.archexamples.subscribe.application.core;

public final class EmailFormatException extends Exception {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
