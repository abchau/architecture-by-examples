package com.github.abchau.oss.archexamples.subscription.domain;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailAddressVerifier {

	private final SubscriptionDataStore subscriptionDataStore;

	public EmailAddressVerifier(final SubscriptionDataStore subscriptionDataStore) {
		this.subscriptionDataStore = subscriptionDataStore;
	}

	@Transactional(readOnly = true)
	public boolean isEmailAlreadyExist(EmailAddress emailAddress) {
		Objects.requireNonNull(emailAddress, "email.empty");
		Objects.requireNonNull(emailAddress.getValue(), "email.empty");

		int count = subscriptionDataStore.countByEmail(emailAddress);
		if (count > 0) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return false;
	}

}
