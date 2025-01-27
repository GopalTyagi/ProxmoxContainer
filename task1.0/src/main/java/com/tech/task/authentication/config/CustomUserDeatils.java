package com.tech.task.authentication.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tech.task.entity.Student;

public class CustomUserDeatils implements UserDetails {

	private static final long serialVersionUID = -1057835463114470628L;
	private String username;
	private String password;

	public CustomUserDeatils(Student student) {

		this.username = student.getName();
		this.password = student.getPassword();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
