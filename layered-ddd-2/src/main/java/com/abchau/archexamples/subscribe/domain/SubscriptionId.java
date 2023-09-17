package com.abchau.archexamples.subscribe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.jmolecules.ddd.annotation.ValueObject;

@AllArgsConstructor(staticName = "of")
@Builder
@Data
@ValueObject
public final class SubscriptionId  {

	private Long value;

}
