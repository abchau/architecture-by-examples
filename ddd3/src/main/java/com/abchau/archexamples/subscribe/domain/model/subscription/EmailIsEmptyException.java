package com.abchau.archexamples.subscribe.domain.model.subscription;

public class EmailIsEmptyException extends Exception {
	
	public static final String CODE = "email.empty";

	public EmailIsEmptyException(String msg) {
		super(msg);
	}

}
