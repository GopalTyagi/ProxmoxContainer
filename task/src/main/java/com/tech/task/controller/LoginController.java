package com.tech.task.controller;

import com.tech.task.entities.Student;
import com.tech.task.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@Autowired
	private StudentRepository repository;

	@GetMapping("/login")
	public String showLoginPage() {

		return "login";
		
		

	}
	

	@PostMapping
	public String processLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model) {
		Student student = repository.findByEmailAndPassword(email, password);
		if (student != null) {
			model.addAttribute("student", student);
			return "welcome";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "login";
		}
	}

}
