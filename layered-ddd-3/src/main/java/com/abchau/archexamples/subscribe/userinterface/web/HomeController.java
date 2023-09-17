package com.abchau.archexamples.subscribe.userinterface.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
final class HomeController {

    @GetMapping(value = "/")
	public String home() {
		return "redirect:/subscribe";
	}

}
