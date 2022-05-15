package com.abchau.archexamples.hddd.subscribe.application.facade.internal;

import java.util.Objects;

import com.abchau.archexamples.hddd.subscribe.application.facade.SubscriptionCommand;
import com.abchau.archexamples.hddd.subscribe.application.facade.SubscriptionFacade;
import com.abchau.archexamples.hddd.subscribe.domain.entity.Subscription;
import com.abchau.archexamples.hddd.subscribe.domain.input.SubscriptionService;
import com.abchau.archexamples.hddd.subscribe.domain.output.SubscriptionPersistencePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class SubscriptionFacadeImpl implements SubscriptionFacade {

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscriptionFacadeImpl(final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public Mono<Subscription> subscribe(SubscriptionCommand subscriptionCommand) {
		// TODO Auto-generated method stub
		return null;
	}
}
