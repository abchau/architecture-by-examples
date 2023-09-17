package com.abchau.archexamples.subscribe.application.usecase;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abchau.archexamples.subscribe.domain.EmailAlreadyExistException;
import com.abchau.archexamples.subscribe.domain.EmailFormatException;
import com.abchau.archexamples.subscribe.domain.Subscription;
import com.abchau.archexamples.subscribe.application.drivenport.EmailVerificationPort;
import com.abchau.archexamples.subscribe.application.drivenport.SaveSubscriptionPort;
import com.abchau.archexamples.subscribe.application.drivingport.CreateSubscriptionUseCasePort;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class CreateSubscriptionUseCase implements CreateSubscriptionUseCasePort {

	private SaveSubscriptionPort saveSubscriptionPort;

	private EmailVerificationPort emailVerificationPort;

	@Autowired
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

