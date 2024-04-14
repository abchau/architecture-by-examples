package com.github.abchau.oss.archexamples.subscription.outputadapter.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.abchau.oss.archexamples.subscription.application.EmailAddress;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;
import com.github.abchau.oss.archexamples.subscription.application.drivenport.EmailVerificationPort;
import com.github.abchau.oss.archexamples.subscription.application.drivenport.SaveSubscriptionPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("test")
@Component
public class InMemorySubscriptionPersistenceAdapter implements SaveSubscriptionPort, EmailVerificationPort {
	
	private Map<Long, Subscription> dataStore = new ConcurrentHashMap<>();

	private ReentrantLock lock = new ReentrantLock();
	
	@Override
	public boolean isEmailAlreadyExist(EmailAddress emailAddress) {
		return Math.toIntExact(dataStore.entrySet().stream()
			.filter(e -> emailAddress.getValue().equalsIgnoreCase(e.getValue().getEmailAddress().getValue()))
			.count()) > 0;	
	}

	@Override
	public Subscription save(Subscription subscription) {
		lock.lock();

		try {
			long nextId = Long.valueOf(dataStore.size()) + 1L;
			subscription.setId(nextId);
			dataStore.put(nextId, subscription);
		} finally {
			lock.unlock();
		}

		return subscription;
	}


}
