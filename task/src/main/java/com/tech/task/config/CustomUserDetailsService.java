package com.tech.task.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tech.task.entities.Student;
import com.tech.task.repositories.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private StudentRepository studentRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepository.findByName(username);
		if (student == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(student.getName(), student.getPassword(),
				new ArrayList<>());
	}


}