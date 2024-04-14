package com.github.abchau.oss.archexamples.subscription.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;

import com.github.abchau.oss.archexamples.subscription.application.EmailAddress;

@SecondaryPort
public interface EmailVerificationPort {

    boolean isEmailAlreadyExist(EmailAddress emailAddress);

}

