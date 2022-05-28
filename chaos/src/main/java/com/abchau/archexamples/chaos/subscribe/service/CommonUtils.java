package com.abchau.archexamples.chaos.subscribe.service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

// (1) dafuq
@Component
public class CommonUtils {
	
	// (1) dafuq
	public boolean valid(String pattern, String string) {
		// (1) dafuq
		return valid(pattern, string, false);
	}

	// (1) dafuq
	public boolean valid(String pattern, String string, /* (1) dafuq */ boolean isCaseSensitive) {
		// (1) dafuq
		Pattern PATTERN = isCaseSensitive ? Pattern.compile(pattern) : Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

		Matcher matcher = PATTERN.matcher(string);

		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	// (1) dafuq
	public ZonedDateTime getCurrentTime() {
		return ZonedDateTime.now(Clock.systemDefaultZone());
	}
}
