package com.abchau.archexamples.subscribe.application.outputport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

import com.abchau.archexamples.subscribe.domain.EmailAddress;


@SecondaryPort
public interface QuerySubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

}

