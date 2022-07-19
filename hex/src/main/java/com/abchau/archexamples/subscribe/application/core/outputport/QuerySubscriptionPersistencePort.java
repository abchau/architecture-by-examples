package com.abchau.archexamples.subscribe.application.core.outputport;

import com.abchau.archexamples.subscribe.application.core.EmailAddress;

public interface QuerySubscriptionPersistencePort {

    int countByEmail(EmailAddress emailAddress);

}

