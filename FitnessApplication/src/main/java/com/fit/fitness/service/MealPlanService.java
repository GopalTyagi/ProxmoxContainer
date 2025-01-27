package com.fit.fitness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.fitness.entity.MealPlan;
import com.fit.fitness.repositories.MealPlanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanService {

	@Autowired
	private MealPlanRepository mealPlanRepository;

	public MealPlan createMealPlan(MealPlan mealPlan) {
		return mealPlanRepository.save(mealPlan);
	}

	public List<MealPlan> findAllMealPlans() {
		return mealPlanRepository.findAll();
	}

	public Optional<MealPlan> findMealPlanById(Long id) {
		return mealPlanRepository.findById(id);
	}

	public MealPlan updateMealPlan(Long id, MealPlan updatedMealPlan) {
		if (mealPlanRepository.existsById(id)) {
			updatedMealPlan.setMealPlanId(id);
			return mealPlanRepository.save(updatedMealPlan);
		} else {
			throw new RuntimeException("Meal Plan not found with ID: " + id);
		}
	}

	public void deleteMealPlan(Long id) {
		if (mealPlanRepository.existsById(id)) {
			mealPlanRepository.deleteById(id);
		} else {
			throw new RuntimeException("Meal Plan not found with ID: " + id);
		}
	}

	public MealPlan getMealPlanByUserId(Long userId) {
		// Fetch the meal plan associated with the given user ID
		return mealPlanRepository.findByUserId(userId);
	}
}