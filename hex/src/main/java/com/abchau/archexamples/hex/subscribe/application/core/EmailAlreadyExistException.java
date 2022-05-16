package com.abchau.archexamples.hex.subscribe.application.core;

public class EmailAlreadyExistException extends Exception {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
