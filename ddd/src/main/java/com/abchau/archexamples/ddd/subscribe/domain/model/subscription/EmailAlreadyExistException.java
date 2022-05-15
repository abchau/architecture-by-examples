package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

public class EmailAlreadyExistException extends RuntimeException {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
