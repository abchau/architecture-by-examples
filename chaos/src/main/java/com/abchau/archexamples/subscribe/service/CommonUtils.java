package com.abchau.archexamples.subscribe.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
	
	public LocalDateTime getCurrentTime() {
		return LocalDateTime.now(Clock.systemDefaultZone());
	}

	private static final String EMAIL_PATTERN_STR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	public boolean isEmailValid(String email) {
		return valid(email);
	}

	public boolean valid(String string) {
		return valid(string, EMAIL_PATTERN_STR);
	}

	public boolean valid(String string, String pattern) {
		return valid(pattern, string, false);
	}

	public boolean valid(String string, String pattern, boolean isCaseSensitive) {
		Pattern PATTERN = isCaseSensitive ? Pattern.compile(pattern) : Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

		Matcher matcher = PATTERN.matcher(string);

		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
