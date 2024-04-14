package com.github.abchau.oss.archexamples.subscription.service;

import java.util.Map;

public abstract class GeneralService {

	public static String getValue(Map<String, String> params, String key) {
		if (params.get(key) != null) {
			return params.get(key);
		} else {
			return null;
		}
	}

	public static String sanitizeInput(String input) {
		return input.replace("$", "");
	}

}
