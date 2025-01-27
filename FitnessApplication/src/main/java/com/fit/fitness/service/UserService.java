package com.fit.fitness.service;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fit.fitness.entity.User;
import com.fit.fitness.exception.UserNotFoundException;
import com.fit.fitness.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public User saveUser(User user) {
		return repository.save(user);
	}

	public User authenticate(String email, String password) throws AuthenticationException {
		User user = repository.findByEmail(email);

		if (user == null) {
			throw new AuthenticationException("Invalid Email ");
		}

		if (!password.equals(user.getPassword())) {
			throw new AuthenticationException("Invalid Password");
		}
		return user;
	}

	/*
	 * public List<User> findAllUsers() { return repository.findAll(); }
	 */
	public List<User> findUsersByRoleId(Long roleId) {
		return repository.findByRoleId(roleId);
	}

	public List<User> getAllUsers() {
		return repository.findAll();
	}

	public User getUserById(Long id) {
		// Fetch the user by ID from the repository
		Optional<User> userOptional = repository.findById(id);

		// Check if user exists and return it; otherwise throw an exception or handle
		// accordingly
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new UserNotFoundException("User not found with ID: " + id); // Custom exception handling
		}
	}

	public User updateUser(Long id, User updatedUser) {
		// Fetch the existing user by ID
		Optional<User> userOptional = repository.findById(id);

		if (userOptional.isPresent()) {
			User existingUser = userOptional.get();

			// Update fields with new values from updatedUser
			existingUser.setName(updatedUser.getName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPhone(updatedUser.getPhone());
			existingUser.setRoleName(updatedUser.getRoleName());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setDob(updatedUser.getDob()); // Ensure dob is in correct format
			existingUser.setGender(updatedUser.getGender());

			// Save the updated user back to the repository
			return repository.save(existingUser);
		} else {
			throw new UserNotFoundException("User not found with ID: " + id); // Custom exception handling
		}
	}
}
