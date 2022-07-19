package com.abchau.archexamples.subscribe.application.outputport;

import com.abchau.archexamples.subscribe.domain.EmailAddress;

public interface QuerySubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

}

