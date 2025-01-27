package com.tech.task.authentication.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tech.task.entity.Student;
import com.tech.task.repo.StudentRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StudentRepository repository;

	private final String SECRET = "tg478gbgbfdhre9yrfsbj23423u409sjbskjvbdsfh9r92";

	private final long EXPIRATION_TIME = 1000 * 60 * 30; // 15 minutes

	public String extractUsername(String token) {
		System.out.println("inside the extractUsername");
		return extractClaim(token, Claims::getSubject);

	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	// generate username

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Check if the token is expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Validate the token against user details and expiration
	public Boolean validateToken(final String token, UserDetails userDetails) {
		final String username = extractUsername(token);

		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String saveUser(Student student) {

		student.setPassword(passwordEncoder.encode(student.getPassword()));
		repository.save(student);
		return "User added successfully";
	}

}