package com.abchau.archexamples.hdddeda.subscribe.domain.exception;

public class EmailFormatException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
