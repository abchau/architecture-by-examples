package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

public class EmailFormatException extends RuntimeException {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
