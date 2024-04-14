package com.github.abchau.oss.archexamples.subscription.domain;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class EmailAddressVerifier {

	private final SubscriptionRepository subscriptionRepository;

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

}
