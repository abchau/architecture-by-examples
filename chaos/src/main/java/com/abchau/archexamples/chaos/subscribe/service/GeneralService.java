package com.abchau.archexamples.chaos.subscribe.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import lombok.extern.log4j.Log4j2;

// (1) dafuq
@Log4j2
public abstract class GeneralService {

	// (1) dafuq
	public static String sanitizeInput(String input) {
		// (2) pretend some work in done in this example
		// beep
		// beep
		// beep

		return input;
	}

	// (1) dafuq
	// public Principal getPrincipal() {
	// 	SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// }

}
