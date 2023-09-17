package com.abchau.archexamples.subscribe.domain;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

// Domain Service
@Log4j2
@Service
public class EmailAddressVerifier {

	private final SubscriptionRepository subscriptionRepository;

	@Autowired
	public EmailAddressVerifier(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	public boolean isEmailAlreadyExist(EmailAddress emailAddress) {
		Objects.requireNonNull(emailAddress, "email.empty");
		Objects.requireNonNull(emailAddress.getValue(), "email.empty");

		int count = subscriptionRepository.countByEmail(emailAddress);
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

	public boolean isBlackListed(EmailAddress emailAddress) {
		throw new UnsupportedOperationException("not yet implemented");
	}

}
