package com.abchau.archexamples.subscribe.application.core;

public class EmailAlreadyExistException extends Exception {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
