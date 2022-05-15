package com.abchau.archexamples.hddd.subscribe.domain.exception;

public class EmailFormatException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
