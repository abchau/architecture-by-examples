package com.abchau.archexamples.hddd.subscribe.domain.exception;

public class EmailAlreadyExistException extends RuntimeException {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
