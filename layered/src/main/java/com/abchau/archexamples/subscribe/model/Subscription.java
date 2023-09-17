package com.abchau.archexamples.subscribe.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Entity(name="subscriptions")
public class Subscription {

	private static final String EMAIL_PATTERN_STR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN_STR, Pattern.CASE_INSENSITIVE);

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
    @NotNull(message = "email.empty")
    @Size(min = 7, max = 512, message = "email.size")
	@Column(name = "email")
	private String email;
	
	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Version
	@Column(name = "version")
	private Long version;

	public Subscription() {
	}

	public static Subscription of(String email) {
		LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());

		return Subscription.builder()
			.email(email)
			.status("INITIALIZED")
			.createdAt(now)
			.build();
	}

	public static boolean isEmailValid(String email) {
		Objects.requireNonNull(email, "email.empty");

        Matcher matcher = PATTERN.matcher(email);

		if (matcher.matches()) {
			return true;
		}

		return false;
	}
	
}
