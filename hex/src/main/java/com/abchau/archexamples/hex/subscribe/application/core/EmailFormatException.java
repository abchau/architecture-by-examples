package com.abchau.archexamples.hex.subscribe.application.core;

public class EmailFormatException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
