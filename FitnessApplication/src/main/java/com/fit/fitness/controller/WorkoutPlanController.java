package com.fit.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fit.fitness.entity.WorkoutPlan;
import com.fit.fitness.service.WorkoutPlanService;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {

	@Autowired
	private WorkoutPlanService workoutPlanService;

	@PostMapping
	public ResponseEntity<WorkoutPlan> createWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
		WorkoutPlan createdWorkoutPlan = workoutPlanService.createWorkoutPlan(workoutPlan);
		return ResponseEntity.status(201).body(createdWorkoutPlan); // Return 201 Created status
	}

	@PutMapping("/{id}")
	public ResponseEntity<WorkoutPlan> updateWorkoutPlan(@PathVariable Long id,
			@RequestBody WorkoutPlan updatedWorkout) {
		try {
			WorkoutPlan updated = workoutPlanService.updateWorkoutPlan(id, updatedWorkout);
			return ResponseEntity.ok(updated); // Return 200 OK status with updated plan
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(null); // Return 404 Not Found if not found
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<WorkoutPlan> getWorkoutById(@PathVariable Long id) {
		try {
			WorkoutPlan workout = workoutPlanService.findWorkoutPlanById(id);
			return ResponseEntity.ok(workout); // Return 200 OK status with the found plan
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(null); // Return 404 Not Found if not found
		}
	}

	@GetMapping
	public ResponseEntity<List<WorkoutPlan>> getAllWorkoutPlans() {
		List<WorkoutPlan> workouts = workoutPlanService.findAllWorkoutPlans();
		return ResponseEntity.ok(workouts); // Return 200 OK status with the list of plans
	}

}