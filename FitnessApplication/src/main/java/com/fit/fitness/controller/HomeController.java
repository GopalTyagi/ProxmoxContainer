package com.fit.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/home/dashboard")
	public String welcome() {
		return "dashboard";
	}
	

	/*
	 * @GetMapping("/home/dashboard") public String dashboard() { return "welcome";
	 * }
	 */

	@GetMapping("/home/login")
	public String loginView() {
		return "login";
	}

	@GetMapping("/home/signup")
	public String SignupView() {
		return "signUp";
	}

}