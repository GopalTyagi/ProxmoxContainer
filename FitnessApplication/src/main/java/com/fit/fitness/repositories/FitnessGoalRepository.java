package com.fit.fitness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fit.fitness.entity.FitnessGoal;

public interface FitnessGoalRepository extends JpaRepository<FitnessGoal, Integer> {

	List<FitnessGoal> findByUserId(int userId);
	// Additional custom query methods can be added here

	FitnessGoal findByUserId(Long userId);
}