package com.abchau.archexamples.hex.subscribe.inputadapter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
	public String home() {
		return "redirect:/subscribe";
	}

}
