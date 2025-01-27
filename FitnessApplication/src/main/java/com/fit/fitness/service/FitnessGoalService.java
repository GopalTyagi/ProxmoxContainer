package com.fit.fitness.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.fitness.entity.FitnessGoal;
import com.fit.fitness.repositories.FitnessGoalRepository;

@Service
public class FitnessGoalService {
	@Autowired
	private FitnessGoalRepository fitnessGoalRepository;

	// Implement service methods using the repository
	public FitnessGoal createFitnessGoal(FitnessGoal goal) {
		return fitnessGoalRepository.save(goal);
	}

	public FitnessGoal getFitnessGoal(int goalId) {
		Optional<FitnessGoal> goal = fitnessGoalRepository.findById(goalId);
		return goal.orElse(null); // Return null if not found
	}

	// Get all fitness goals for a user
	public List<FitnessGoal> getAllGoalsForUser(int userId) {
		return fitnessGoalRepository.findByUserId(userId); // Assuming you have this method in your repository
	}

	// Update an existing fitness goal
	public FitnessGoal updateFitnessGoal(FitnessGoal updatedGoal) {
		if (fitnessGoalRepository.existsById(updatedGoal.getGoalId())) { // Check if the goal exists
			return fitnessGoalRepository.save(updatedGoal); // Save the updated goal
		}
		return null; // Return null if the goal does not exist
	}

	// Delete a fitness goal by ID
	public void deleteFitnessGoals(int goalId) {
		if (fitnessGoalRepository.existsById(goalId)) { // Check if the goal exists before deleting
			fitnessGoalRepository.deleteById(goalId);
		}
	}

	  public FitnessGoal getFitnessDataByUserId(Long userId) {
	        // Fetch the fitness data associated with the given user ID
	        return fitnessGoalRepository.findByUserId(userId);
	    }
}