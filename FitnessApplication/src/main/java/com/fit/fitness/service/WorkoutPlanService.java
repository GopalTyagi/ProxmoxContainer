package com.fit.fitness.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.fitness.entity.WorkoutPlan;
import com.fit.fitness.repositories.WorkoutPlanRepository;

@Service
public class WorkoutPlanService {

	@Autowired
	private WorkoutPlanRepository workoutPlanRepository;

	public WorkoutPlan createWorkoutPlan(WorkoutPlan workoutPlan) {
		return workoutPlanRepository.save(workoutPlan);
	}

	public WorkoutPlan updateWorkoutPlan(Long workoutPlanId, WorkoutPlan updatedWorkoutPlan) {
		Optional<WorkoutPlan> existingWorkoutPlan = workoutPlanRepository.findById(workoutPlanId);
		if (existingWorkoutPlan.isPresent()) {
			WorkoutPlan workoutPlan = existingWorkoutPlan.get();
			workoutPlan.setUserId(updatedWorkoutPlan.getUserId());
			workoutPlan.setWorkoutName(updatedWorkoutPlan.getWorkoutName());
			workoutPlan.setDescription(updatedWorkoutPlan.getDescription());
			workoutPlan.setExercisesList(updatedWorkoutPlan.getExercisesList());
			workoutPlan.setDuration(updatedWorkoutPlan.getDuration());
			workoutPlan.setFrequency(updatedWorkoutPlan.getFrequency());

			return workoutPlanRepository.save(workoutPlan); // Save and return updated plan
		} else {
			throw new RuntimeException("Workout Plan not found with ID: " + workoutPlanId);
		}
	}

	public WorkoutPlan findWorkoutPlanById(Long id) {
		return workoutPlanRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Workout Plan not found with ID: " + id));
	}

	public List<WorkoutPlan> findAllWorkoutPlans() {
		return workoutPlanRepository.findAll();
	}

	 public WorkoutPlan getWorkoutPlanByUserId(Long userId) {
	        // Fetch the workout plan associated with the given user ID
	        return workoutPlanRepository.findByUserId(userId);
	    }

}