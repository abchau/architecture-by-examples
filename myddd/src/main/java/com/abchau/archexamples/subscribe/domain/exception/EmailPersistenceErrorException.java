package com.abchau.archexamples.subscribe.domain.exception;

public class EmailPersistenceErrorException extends Exception {
	
	public static final String CODE = "error.persistence";

	public EmailPersistenceErrorException(String msg) {
		super(msg);
	}

}
