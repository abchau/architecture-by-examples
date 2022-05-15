package com.abchau.archexamples.hex.subscribe.application.core;

public class EmailAlreadyExistException extends RuntimeException {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
