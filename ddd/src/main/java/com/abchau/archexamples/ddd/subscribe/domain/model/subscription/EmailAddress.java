package com.abchau.archexamples.ddd.subscribe.domain.model.subscription;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 
 * @dddarch-name Value Object
 * @hexarch-name ?
 */
@AllArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
@ToString
public class EmailAddress {
	
	private static final String EMAIL_PATTERN_STR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN_STR, Pattern.CASE_INSENSITIVE);

    @NotNull(message = "email.empty")
    @Size(min = 7, max = 512, message = "email.size")
	private String value;

	public boolean isValidFormat() {
		Objects.requireNonNull(value, "email.empty");

        Matcher matcher = PATTERN.matcher(this.value);
		
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
}
