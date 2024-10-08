package com.github.abchau.oss.archexamples.subscription.domain;

public final class SubscriptionPersistenceException extends SubscriptionException {
	
	public static final String CODE = "error.create";

	public SubscriptionPersistenceException(String msg) {
		super(msg);
	}

    public SubscriptionPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
