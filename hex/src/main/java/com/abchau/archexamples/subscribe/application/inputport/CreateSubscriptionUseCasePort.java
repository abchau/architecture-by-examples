package com.abchau.archexamples.subscribe.application.inputport;

import org.jmolecules.architecture.hexagonal.PrimaryPort;

import com.abchau.archexamples.subscribe.domain.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.Subscription;

@PrimaryPort
public interface CreateSubscriptionUseCasePort {

	public Subscription execute(Subscription subscription) throws EmailFormatException, EmailAlreadyExistException, Exception;

}
