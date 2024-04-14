package com.github.abchau.oss.archexamples.subscription.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abchau.oss.archexamples.subscription.repository.SubscriptionDataService;
import com.github.abchau.oss.archexamples.subscription.service.SubscriptionUtil;

@Slf4j
@Controller
public class SubscriptionController {

	@Autowired
	private SubscriptionUtil subscriptionUtil;

	public SubscriptionController() {}

    @GetMapping(value = "/")
	public String home() {
		return "redirect:/create";
	}

	@GetMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addObject("message", "");

		return modelAndView;
	}

	@PostMapping(value = "/create")
	public ModelAndView save(@RequestBody MultiValueMap<String, String> params) {
		params.put("email", List.of(subscriptionUtil.sanitizeInput(params.getFirst("email"))));

		Map subscriptionMap = new HashMap<>();
		
		if (params.getFirst("email") != null && !"".equalsIgnoreCase(params.getFirst("email"))) {
			params.add("status", "NEW");

			Map mapObj = new HashMap<>();
			mapObj.put("email", params.getFirst("email"));
			subscriptionMap = subscriptionUtil.save(mapObj);

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
		} else {
			subscriptionMap.put("message", "email.empty");
		}

		ModelAndView modelAndView = new ModelAndView("subscribe");
		modelAndView.addAllObjects(subscriptionMap);

		return modelAndView;
	}

}
