package com.abchau.archexamples.hdddeda.subscribe.domain.event;

import java.util.Map;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class SubscriptionRequestedEvent extends DomainEvent {

	protected final String type = getClass().toString();

	protected final String source = "historia.subscribe";

	protected final String subject = "REQUESTED_EVENT";

	protected final String version = "historia/subscribe/Subscription/requested/v1";

	protected Subscription data;

	public static SubscriptionRequestedEvent of(final Subscription data) {
		return SubscriptionRequestedEvent.builder()
				.data(data)
				.build();
	}

	public static SubscriptionRequestedEvent of(final Subscription data, final Map<String, Object> customMetadata) {
		final SubscriptionRequestedEvent event = SubscriptionRequestedEvent.builder()
				.data(data)
				.build();

		event.addMetadata(customMetadata);

		return event;
	}

}
