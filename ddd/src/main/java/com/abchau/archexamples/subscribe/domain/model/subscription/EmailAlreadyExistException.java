package com.abchau.archexamples.subscribe.domain.model.subscription;

public final class EmailAlreadyExistException extends Exception {
	
	public static final String CODE = "email.duplicate";

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}

}
