package com.fit.fitness.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "workout_plans")
public class WorkoutPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workoutPlanId; // Unique identifier for the workout plan

	@Column(nullable = false)
	private Long userId; // Identifier of the user creating the plan

	@Column(nullable = false)
	private String workoutName; // Name of the workout plan

	@Column(length = 500) // Limit description length to 500 characters
	private String description; // Detailed description of the workout plan

	@ElementCollection // To store a list of exercises
	private List<String> exercisesList; // List of exercises included in the plan

	@Column(nullable = false)
	private int duration; // Total time required to complete the workout (in minutes)

	@Column(nullable = false)
	private String frequency; // How often the workout should be performed (e.g., daily, weekly)

	// Getters and Setters
	public Long getWorkoutPlanId() {
		return workoutPlanId;
	}

	public void setWorkoutPlanId(Long workoutPlanId) {
		this.workoutPlanId = workoutPlanId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getExercisesList() {
		return exercisesList;
	}

	public void setExercisesList(List<String> exercisesList) {
		this.exercisesList = exercisesList;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}