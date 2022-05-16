package com.abchau.archexamples.hex.subscribe.application.core;

public class EmailFormatException extends Exception {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
