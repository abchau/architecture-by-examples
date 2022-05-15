package com.abchau.archexamples.hddd.subscribe.domain.exception;

public class EmailPersistenceErrorException extends RuntimeException {
	
	public static final String CODE = "error.persistence";

	public EmailPersistenceErrorException(String msg) {
		super(msg);
	}

}
