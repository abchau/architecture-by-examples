package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

public class EmailPersistenceErrorException extends RuntimeException {
	
	public static final String CODE = "error.persistence";

	public EmailPersistenceErrorException(String msg) {
		super(msg);
	}

}
