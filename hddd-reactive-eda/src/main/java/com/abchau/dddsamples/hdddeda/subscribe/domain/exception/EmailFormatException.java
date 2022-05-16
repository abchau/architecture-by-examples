package com.abchau.archexamples.hdddeda.subscribe.domain.exception;

public class EmailFormatException extends Exception {
	
	public static final String CODE = "email.format";

	public EmailFormatException(String msg) {
		super(msg);
	}

}
