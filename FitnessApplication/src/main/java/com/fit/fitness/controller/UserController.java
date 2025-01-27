package com.fit.fitness.controller;

import java.util.List;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fit.fitness.config.JwtUtil;
import com.fit.fitness.entity.FitnessGoal;
import com.fit.fitness.entity.MealPlan;
import com.fit.fitness.entity.User;
import com.fit.fitness.entity.WorkoutPlan;
import com.fit.fitness.exception.UserNotFoundException;
import com.fit.fitness.service.FitnessGoalService;
import com.fit.fitness.service.MealPlanService;
import com.fit.fitness.service.UserService;
import com.fit.fitness.service.WorkoutPlanService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private MealPlanService mealPlanService;

	@Autowired
	private WorkoutPlanService workoutPlanService;

	@Autowired
	private FitnessGoalService fitnessGoalService;

	@Autowired
	private JwtUtil jwtUtil;

	// @Autowired private PasswordEncoder encoder;

	@PostMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		// Get the current authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			// Perform logout by invalidating the session
			SecurityContextHolder.clearContext();
			request.getSession().invalidate(); // Invalidate the session
		}

		// Add a success message to the model
		model.addAttribute("logoutSuccess", "You have been logged out successfully.");

		// Redirect to the login page
		return "login"; // This should match the name of your login view template (e.g., login.html)
	}

	@RequestMapping("/error")
	public String handleError() {
		// Return a custom error page
		return "error"; // Name of your error page template
	}

	@PostMapping(value = "/add")
	public String addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password,
			@RequestParam(required = false) String roleName, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String dob, @RequestParam(required = false) String gender, Model model) {

		Long defaultRoleId = 2L; // Replace with the appropriate default role ID

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password); // Optionally encode the password
		user.setRoleId(defaultRoleId);
		user.setRoleName(roleName);
		user.setPhone(phone);
		user.setDob(dob);
		user.setGender(gender);

		// Save the user
		service.saveUser(user);

		// Add success message to the model
		model.addAttribute("successMessage", "User registered successfully!");

		return "signUp"; // Return to the sign-up view
	}

	@PostMapping("/login")
	public String authenticateUser(@RequestParam String email, @RequestParam String password, Model model) {
		try {
			User authenticatedUser = service.authenticate(email, password);
			if (authenticatedUser != null) {
				// Generate token
				String token = jwtUtil.generateToken(authenticatedUser.getName());
				model.addAttribute("user", authenticatedUser);
				model.addAttribute("token", token); // Add token to the model

				MealPlan mealPlan = mealPlanService.getMealPlanByUserId(authenticatedUser.getId());
				WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlanByUserId(authenticatedUser.getId());
				FitnessGoal fitnessData = fitnessGoalService.getFitnessDataByUserId(authenticatedUser.getId());

				model.addAttribute("mealPlan", mealPlan);
				model.addAttribute("workoutPlan", workoutPlan);
				model.addAttribute("fitnessData", fitnessData);

				// Check user role
				if ("admin".equalsIgnoreCase(authenticatedUser.getRoleName())) {
					// If the user is an admin, fetch all users
					List<User> allUsers = service.getAllUsers(); // Assuming you have a method to get all users
					model.addAttribute("users", allUsers); // Add all users to the model
					return "Userlist"; // Return the admin dashboard view name
				} else if ("user".equalsIgnoreCase(authenticatedUser.getRoleName())) {

					// If the user is a regular user, return only their data
					return "welcome"; // Return the welcome view for regular users
				}
			} else {
				model.addAttribute("error", "Invalid credentials");
				return "login"; // Return the login view name
			}
		} catch (AuthenticationException e) {
			model.addAttribute("error", "Authentication failed: " + e.getMessage());
			return "login"; // Return the login view name
		}
		return "login"; // Fallback in case of unexpected conditions
	}

	@GetMapping("/{id}")
	public String editUser(@PathVariable Long id, Model model) {
		try {
			User user = service.getUserById(id); // Fetch user by ID
			model.addAttribute("user", user); // Add user data to the model
			return "editUser"; // Return the signup view (edit form)
		} catch (UserNotFoundException e) {
			return "redirect:/error"; // Handle user not found (redirect or show error)
		}
	}

	@PostMapping("/{id}/update")
	public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser, Model model) {
		try {
			User userupdate = service.updateUser(id, updatedUser);
			model.addAttribute("message", "User updated successfully!");
			model.addAttribute("users", service.getAllUsers());
			return "Userlist";
		} catch (Exception e) {
			model.addAttribute("error", "Error updating user: " + e.getMessage());
			return "userEdit";
		}
	}

	/*
	 * @GetMapping public ResponseEntity<List<User>> getAllUsers() { List<User>
	 * users = service.findAllUsers(); return ResponseEntity.ok(users); }
	 */

	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<User>> getUsersByRoleId(@PathVariable Long roleId) {
		List<User> users = service.findUsersByRoleId(roleId);
		return ResponseEntity.ok(users);
	}

}
