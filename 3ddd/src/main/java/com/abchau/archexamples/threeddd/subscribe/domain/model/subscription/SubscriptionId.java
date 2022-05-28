package com.abchau.archexamples.threeddd.subscribe.domain.model.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Builder
@Data
public final class SubscriptionId {

	private Long value;

}
