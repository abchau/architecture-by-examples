package com.abchau.archexamples.subscribe.interfaces.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
	public String home() {
		return "redirect:/subscribe";
	}

}
