package com.github.abchau.oss.archexamples.subscription.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Version;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;


import com.github.abchau.oss.archexamples.subscription.service.CommonUtils;

import lombok.Data;

@Data
@Entity(name="subscriptions")
public class Subscription extends CommonObjectType {

	private static final int OBJECT_TYPE_ID = 123;

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
	private LocalDateTime createdAt;

	@Version
	@Column(name = "version")
	private Long version;

	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	protected String getObjectType() {
		return Subscription.class.getSimpleName();
	}

}
