package com.tech.task.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.tech.task.authentication.config.AuthRequest;
import com.tech.task.authentication.config.JwtUtil;
import com.tech.task.entity.Student;

@RestController
@RequestMapping("/user")
public class AuthController {

	@Autowired
	private JwtUtil util;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService; // Inject your 
	
	@PostMapping("/register")
	public String addUser(@RequestBody Student student) {
		return util.saveUser(student);
	}

	@PostMapping("/generateToken")
	public String getToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return util.generateToken(authRequest.getUsername());

		} else {
			throw new RuntimeException("invalid access......");
		}

	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to the user details";
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token ) {
	    String username = util.extractUsername(token); // Extract username from token
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		System.err.println("token = "+token);
		if (userDetails == null) {
	        throw new UsernameNotFoundException("User not found with username: " + username);
	    }

	    if (util.validateToken(token, userDetails)) {
			System.err.println("inside the valid if  = "+token);

	        return "Token is valid.";
	    } else {
	        return "Token is invalid or expired.";
	    }
	}

}
