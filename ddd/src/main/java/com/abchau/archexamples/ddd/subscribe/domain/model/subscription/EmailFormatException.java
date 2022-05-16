package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

public class EmailFormatException extends Exception {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
