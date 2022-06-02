package com.abchau.archexamples.subscribe.model;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;

import com.abchau.archexamples.subscribe.service.CommonUtils;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
@Entity(name="subscriptions")
public class Subscription extends CommonObjectType { // (1) dafuq

	private static final String EMAIL_PATTERN_STR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	// (1) dafuq
	private static final int OBJECT_TYPE_ID = 123;

	// (1) dafuq
	@Autowired
	@Transient
	private CommonUtils commonUtils;

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

	@Version
	@Column(name = "version")
	private Long version;

	// (1) dafuq
	public boolean isEmailValid(String email) {
		Objects.requireNonNull(email, "email.empty");

		return commonUtils.valid(EMAIL_PATTERN_STR, email);
	}

	// (1) dafuq
	@Override
	public Long getId() {
		return this.id;
	}
	
	// (1) dafuq
	@Override
	protected String getObjectType() {
		// (1) dafuq
		return Subscription.class.getSimpleName();
	}

}
