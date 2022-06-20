package com.abchau.archexamples.subscribe.infrastructure.persistence.jpa;

import java.time.ZonedDateTime;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Entity(name="subscriptions")
final class SubscriptionPersistence {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_generator")
	@SequenceGenerator(name = "subscription_generator", sequenceName = "seq_subscriptions_id", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String status;

	@Column(name = "subscribed_at")
	private ZonedDateTime subscribedAt;

	@Column(name = "confirmed_at")
	private ZonedDateTime confirmedAt;

	@Version
	@Column(name = "version")
	private Long version;

}
