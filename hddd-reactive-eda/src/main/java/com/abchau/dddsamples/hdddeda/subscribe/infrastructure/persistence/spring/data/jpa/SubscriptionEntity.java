package com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.jpa;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.abchau.archexamples.hdddeda.subscribe.domain.entity.EmailAddress;
import com.abchau.archexamples.hdddeda.subscribe.domain.entity.Subscription;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("Subscriptions")
public class SubscriptionEntity {

	@Id
	@Column("id")
	private byte[] id;

	@Column("email")
	private String email;

	@Column("username")
	private String username;

	@Column("password")
	private String password;

	@Column("status")
	private String status;

	@Column("created_at")
	private ZonedDateTime createdAt;

	@Column("last_updated_at")
	private ZonedDateTime lastUpdatedAt;

	// never update this manually
	@Version
	@Column("version")
	private Long version;

}
