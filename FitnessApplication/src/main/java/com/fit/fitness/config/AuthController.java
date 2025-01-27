package com.fit.fitness.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.fit.fitness.entity.User;

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
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
			String token = jwtUtil.generateToken(user.getName());
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}
