package com.abchau.archexamples.subscribe.domain;

public final class SubscriptionPersistenceException extends RuntimeException {
	
	public static final String CODE = "error.create";

	public SubscriptionPersistenceException(String msg) {
		super(msg);
	}

    public SubscriptionPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
