package com.fit.fitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fit.fitness.entity.MealPlan;
import com.fit.fitness.service.MealPlanService;

import java.util.List;

@RestController
@RequestMapping("meal")
public class MealPlanController {
	
	@Autowired
	private MealPlanService mealPlanService;

	@PostMapping
	public ResponseEntity<MealPlan> createMealPlan(@RequestBody MealPlan mealPlan) {
		MealPlan createdMeal = mealPlanService.createMealPlan(mealPlan);
		return ResponseEntity.status(201).body(createdMeal); // Return 201 Created status
	}

	@GetMapping("/{id}")
	public ResponseEntity<MealPlan> getMealById(@PathVariable Long id) {
		return ResponseEntity.of(mealPlanService.findMealPlanById(id)); // Return 200 OK with found plan or 404 if not
																		// found
	}

	@GetMapping
	public ResponseEntity<List<MealPlan>> getAllMealPlans() {
		List<MealPlan> meals = mealPlanService.findAllMealPlans();
		return ResponseEntity.ok(meals); // Return 200 OK status with list of plans
	}

	@PutMapping("/{id}")
	public ResponseEntity<MealPlan> updateMeal(@PathVariable Long id, @RequestBody MealPlan updatedMeal) {
		try {
			MealPlan updated = mealPlanService.updateMealPlan(id, updatedMeal);
			return ResponseEntity.ok(updated); // Return 200 OK status with updated plan
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(null); // Return 404 Not Found if not found
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
		try {
			mealPlanService.deleteMealPlan(id);
			return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(null); // Return 404 Not Found if not found
		}
	}
}