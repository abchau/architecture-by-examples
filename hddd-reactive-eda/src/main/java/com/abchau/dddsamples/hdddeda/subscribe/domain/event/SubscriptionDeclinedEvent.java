package com.abchau.archexamples.hdddeda.subscribe.domain.event;

import java.util.Map;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Data
public class SubscriptionDeclinedEvent extends DomainEvent {

	protected final String type = getClass().toString();

	protected final String source = "historia.subscribe";

	protected final String subject = "DECLINED_EVENT";

	protected final String version = "historia/subscribe/Subscription/declined/v1";

	protected Subscription data;

	public static SubscriptionDeclinedEvent of(final Subscription data) {
		return SubscriptionDeclinedEvent.builder()
				.data(data)
				.build();
	}

	public static SubscriptionDeclinedEvent of(final Subscription data, final Map<String, Object> customMetadata) {
		final SubscriptionDeclinedEvent event = SubscriptionDeclinedEvent.builder()
				.data(data)
				.build();

		event.addMetadata(customMetadata);

		return event;
	}

}
