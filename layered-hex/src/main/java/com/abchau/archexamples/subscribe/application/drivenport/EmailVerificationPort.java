package com.abchau.archexamples.subscribe.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

import com.abchau.archexamples.subscribe.domain.EmailAddress;

@SecondaryPort
public interface EmailVerificationPort {

    boolean isEmailAlreadyExist(EmailAddress emailAddress);

}

