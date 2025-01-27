package com.fit.fitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fit.fitness.entity.FitnessGoal;
import com.fit.fitness.service.FitnessGoalService;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class FitnessGoalController {

	@Autowired(required = true)
	private FitnessGoalService fitnessGoalService;

	@PostMapping
	public ResponseEntity<FitnessGoal> createFitnessGoal(@RequestBody FitnessGoal goal) {
		fitnessGoalService.createFitnessGoal(goal);
		return new ResponseEntity<>(goal, HttpStatus.CREATED);
	}

	// Get a fitness goal by ID  Working
	@GetMapping("/{goalId}")
	public ResponseEntity<FitnessGoal> getFitnessGoal(@PathVariable int goalId) {
		FitnessGoal goal = fitnessGoalService.getFitnessGoal(goalId);
		return (goal != null) ? new ResponseEntity<>(goal, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Get all fitness goals for a user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<FitnessGoal>> getAllGoalsForUser(@PathVariable int userId) {
		List<FitnessGoal> goals = fitnessGoalService.getAllGoalsForUser(userId);
		return new ResponseEntity<>(goals, HttpStatus.OK);
	}

	// Update an existing fitness goal
	@PutMapping("/{goalId}")
	public ResponseEntity<FitnessGoal> updateFitnessGoal(@PathVariable int goalId,
			@RequestBody FitnessGoal updatedGoals) {
		updatedGoals.setGoalId(goalId); // Ensure the ID is set for the update operation
		fitnessGoalService.updateFitnessGoal(updatedGoals);
		return new ResponseEntity<>(updatedGoals, HttpStatus.OK);
	}

	// Delete a fitness goal by ID
	@DeleteMapping("/{goalId}")
	public ResponseEntity<Void> deleteFitnessGoals(@PathVariable int goalId) {
		fitnessGoalService.deleteFitnessGoals(goalId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
