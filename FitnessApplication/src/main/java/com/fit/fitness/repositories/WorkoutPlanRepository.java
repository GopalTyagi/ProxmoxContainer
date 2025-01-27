package com.fit.fitness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fit.fitness.entity.WorkoutPlan;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

	WorkoutPlan findByUserId(Long userId);
}