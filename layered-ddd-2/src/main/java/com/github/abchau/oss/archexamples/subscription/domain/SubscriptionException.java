package com.github.abchau.oss.archexamples.subscription.domain;

public class SubscriptionException extends RuntimeException {
	
	public SubscriptionException(String msg) {
		super(msg);
	}

    public SubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

}
