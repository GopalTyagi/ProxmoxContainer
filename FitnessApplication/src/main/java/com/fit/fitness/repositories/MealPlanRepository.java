package com.fit.fitness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fit.fitness.entity.MealPlan;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

	MealPlan findByUserId(Long userId);
}
