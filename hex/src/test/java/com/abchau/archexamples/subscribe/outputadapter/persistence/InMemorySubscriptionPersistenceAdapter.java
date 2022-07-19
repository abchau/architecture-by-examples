package com.abchau.archexamples.subscribe.outputadapter.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abchau.archexamples.subscribe.domain.EmailAddress;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.application.outputport.CreateSubscriptionPersistencePort;
import com.abchau.archexamples.subscribe.application.outputport.QuerySubscriptionPersistencePort;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Profile("test")
@Component
public class InMemorySubscriptionPersistenceAdapter implements CreateSubscriptionPersistencePort, QuerySubscriptionPersistencePort {
	
	private Map<Long, Subscription> dataStore = new ConcurrentHashMap<>();

	private ReentrantLock lock = new ReentrantLock();
	
	@Override
	public int countByEmail(EmailAddress emailAddress) {
		log.trace("countByEmail()...invoked");

		log.debug("emailAddress: {}", () -> emailAddress);

		return Math.toIntExact(dataStore.entrySet().stream()
			.filter(e -> emailAddress.getValue().equalsIgnoreCase(e.getValue().getEmailAddress().getValue()))
			.count());
	}

	@Override
	public Subscription save(Subscription subscription) {
		log.trace("save()...invoked");

		lock.lock();

		try {
			long nextId = Long.valueOf(dataStore.size()) + 1L;
			subscription.setId(nextId);
			dataStore.put(nextId, subscription);
		} finally {
			lock.unlock();
		}

		log.debug("subscription: {}", () -> subscription);

		return subscription;
	}

}
