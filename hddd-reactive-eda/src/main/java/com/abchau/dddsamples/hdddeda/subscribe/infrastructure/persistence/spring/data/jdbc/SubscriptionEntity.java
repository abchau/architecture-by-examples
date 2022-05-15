package com.abchau.archexamples.hdddeda.subscribe.infrastructure.persistence.spring.data.jdbc;

import java.time.ZonedDateTime;

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

	// /**
	//  * Convert a Domain entity to Database entity
	//  * 
	//  * @param Subscription
	//  * @return
	//  */
	// public static SubscriptionEntity fromDomain(final Subscription subscription) {
	// 	return SubscriptionEntity.builder()
	// 			.id(subscription.getId().toString().getBytes())
	// 			.email(subscription.getEmailAddress().getValue())
	// 			.username(subscription.getUsername())
	// 			.password(subscription.getPassword())
	// 			.status(subscription.getStatus().toString())
	// 			.createdAt(subscription.getCreatedAt())
	// 			.lastUpdatedAt(subscription.getLastUpdatedAt())
	// 			.version(subscription.getVersion())
	// 			.build();
	// }

	// /**
	//  * Convert a Database entity to Domain entity
	//  * 
	//  * @param SubscriptionEntity
	//  * @return
	//  */
	// public static Subscription toDomain(final SubscriptionEntity SubscriptionEntity) {
	// 	return Subscription.builder()
	// 			.id(UUID.fromString(new String(SubscriptionEntity.getId())))
	// 			.emailAddress(EmailAddress.of(SubscriptionEntity.getEmail()))
	// 			.username(SubscriptionEntity.getUsername())
	// 			.password(SubscriptionEntity.getPassword())
	// 			.status(subscription.Status.valueOf(SubscriptionEntity.getStatus()))
	// 			.createdAt(SubscriptionEntity.getCreatedAt())
	// 			.lastUpdatedAt(SubscriptionEntity.getLastUpdatedAt())
	// 			.version(SubscriptionEntity.getVersion())
	// 			.build();
	// }

}
