package com.abchau.archexamples.hdddeda.subscribe.domain.output;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;

public interface SubscriptionEventPublisher {
	
	public void requested(final Subscription subscription);
	
	public void created(final Subscription subscription);
	
	public void declined(final Subscription subscription);
	
}
