package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

public class EmailAlreadyExistException extends Exception {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
