package com.tech.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.tech.task.entities.Student;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Student student) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(student.getName(), student.getPassword()));
			String token = jwtUtil.generateToken(student.getName());
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}
