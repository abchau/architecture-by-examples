package com.abchau.archexamples.subscribe.domain.model.subscription;

public interface SubscriptionService {

	public boolean isEmailAlreadyExist(EmailAddress emailAddress) throws EmailIsEmptyException, EmailAlreadyExistException;

	public Subscription createSubscription(Subscription subscription) throws EmailIsEmptyException, EmailFormatException, EmailAlreadyExistException, InvalidSubscriptionStatusException, CannotCreateSubscriptionException;

}
