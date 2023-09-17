package com.abchau.archexamples.subscribe.model;

import java.time.LocalDateTime;
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
