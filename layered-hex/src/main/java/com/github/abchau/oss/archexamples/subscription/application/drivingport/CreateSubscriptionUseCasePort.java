package com.github.abchau.oss.archexamples.subscription.application.drivingport;

import org.jmolecules.architecture.hexagonal.PrimaryPort;

import com.github.abchau.oss.archexamples.subscription.application.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.application.EmailFormatException;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;

@PrimaryPort
public interface CreateSubscriptionUseCasePort {

	public Subscription execute(Subscription subscription) throws EmailFormatException, EmailAlreadyExistException;

}
