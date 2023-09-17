package com.abchau.archexamples.subscribe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abchau.archexamples.subscribe.repository.SubscriptionRepository;
import com.abchau.archexamples.subscribe.service.SubscriptionService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class SubscribeController {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private SubscriptionService subscriptionService;

	public SubscribeController() {}

	@GetMapping(value = "/subscribe")
	public ModelAndView create() {
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

	@PostMapping(value = "/subscribe")
	public ModelAndView save(@RequestBody MultiValueMap<String, String> params) {
		params.put("email", List.of(subscriptionService.sanitizeInput(params.getFirst("email"))));

		Map subscriptionMap = new HashMap<>();
		
		if (params.getFirst("email") != null && !"".equalsIgnoreCase(params.getFirst("email"))) {
			params.add("status", "INITIALIZED");

			Map mapObj = new HashMap<>();
			mapObj.put("email", params.get("email"));
			subscriptionMap = subscriptionService.save(mapObj);

			if (subscriptionMap.get("subscription") == null) {
				subscriptionMap.put("message", "email.empty");
			} else {
				if (subscriptionMap.get("error") != null) {
					subscriptionMap.put("message", subscriptionMap.get("error"));

					if (subscriptionMap.get("error").equals("empty")) {
						subscriptionMap.put("message", "email.empty");
					} else if (subscriptionMap.get("error").equals("format")) {
						subscriptionMap.put("message", "email.format");
					} else if (subscriptionMap.get("error").equals("duplicate")) {
						subscriptionMap.put("message", "email.duplicate");
					}
				} else {
					subscriptionMap.put("message", "success");
				}
			}
		} else {
			subscriptionMap.put("message", "email.empty");
		}

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addAllObjects(subscriptionMap);

		return modelAndView;
	}

}
