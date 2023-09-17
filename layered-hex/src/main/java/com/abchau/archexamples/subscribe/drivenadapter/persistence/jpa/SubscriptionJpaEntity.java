package com.abchau.archexamples.subscribe.drivenadapter.persistence.jpa;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
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
final class SubscriptionJpaEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Version
	@Column(name = "version")
	private Long version;

}
