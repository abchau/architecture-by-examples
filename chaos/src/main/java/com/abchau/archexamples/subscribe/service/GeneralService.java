package com.abchau.archexamples.subscribe.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.util.MultiValueMap;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class GeneralService {

	public static String getValue(Map<String, String> params, String key) {
		if (params.get(key) != null) {
			return params.get(key);
		} else {
			return null;
		}
	}

	public static String sanitizeInput(String input) {
		// fake implementation
		return input;
	}


}
