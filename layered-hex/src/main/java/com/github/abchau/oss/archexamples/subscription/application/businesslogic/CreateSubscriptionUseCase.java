package com.github.abchau.oss.archexamples.subscription.application.businesslogic;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abchau.oss.archexamples.subscription.application.EmailAlreadyExistException;
import com.github.abchau.oss.archexamples.subscription.application.Subscription;
import com.github.abchau.oss.archexamples.subscription.application.drivenport.EmailVerificationPort;
import com.github.abchau.oss.archexamples.subscription.application.drivenport.SaveSubscriptionPort;
import com.github.abchau.oss.archexamples.subscription.application.drivingport.CreateSubscriptionUseCasePort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class CreateSubscriptionUseCase implements CreateSubscriptionUseCasePort {

	private SaveSubscriptionPort saveSubscriptionPort;
	private EmailVerificationPort emailVerificationPort;

	public CreateSubscriptionUseCase(
		final SaveSubscriptionPort saveSubscriptionPort,
		final EmailVerificationPort emailVerificationPort
	) {
		this.saveSubscriptionPort = saveSubscriptionPort;
		this.emailVerificationPort = emailVerificationPort;
	}

	@Transactional
	public Subscription execute(Subscription subscription) {
		Objects.requireNonNull(subscription);

		boolean isEmailAlreadyExist = emailVerificationPort.isEmailAlreadyExist(subscription.getEmailAddress());
		if (isEmailAlreadyExist) {
			throw new EmailAlreadyExistException("email.duplicate");
		}

		return saveSubscriptionPort.save(subscription);
	}

}

