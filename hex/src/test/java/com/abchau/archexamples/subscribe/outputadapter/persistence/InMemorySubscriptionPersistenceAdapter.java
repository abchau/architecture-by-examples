package com.abchau.archexamples.subscribe.outputadapter.persistence;

import java.util.Objects;

import com.abchau.archexamples.subscribe.application.core.EmailAddress;
import com.abchau.archexamples.subscribe.application.core.Subscription;
import com.abchau.archexamples.subscribe.application.outputport.CreateSubscriptionPersistencePort;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Profile("test")
@Component
public class InMemorySubscriptionPersistenceAdapter implements CreateSubscriptionPersistencePort {
	
	
	@Override
	public int countByEmail(EmailAddress emailAddress) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Subscription save(Subscription subscriptionPersistence) {
		// TODO Auto-generated method stub
		return null;
	}

}
