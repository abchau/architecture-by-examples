package com.abchau.archexamples.hddd.subscribe.infrastructure.persistence.jpa;

import java.time.ZonedDateTime;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Data;

@Data
@Getter
@EqualsAndHashCode
@ToString
@Entity(name="subscriptions")
public class SubscriptionPersistence {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	@Column(name = "last_updated_at")
	private ZonedDateTime lastUpdatedAt;

	@Version
	@Column(name = "version")
	private Long version;

}
