package com.abchau.archexamples.subscribe.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.jmolecules.ddd.annotation.ValueObject;

@AllArgsConstructor
@Builder
@Data
@ValueObject
public final class EmailAddress {
	
	private static final String EMAIL_PATTERN_STR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN_STR, Pattern.CASE_INSENSITIVE);

    @NotNull(message = "email.empty")
    @Size(min = 7, max = 512, message = "email.size")
	private String value;

	public static EmailAddress of(String email) {
		if (!isValidFormat(email)) {
			throw new EmailFormatException("email.format");
		}

		return EmailAddress.builder()
			.value(email)
			.build();
	}

	public static boolean isValidFormat(String email) {
		Objects.requireNonNull(email, "email.empty");

        Matcher matcher = PATTERN.matcher(email);
		
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
}
