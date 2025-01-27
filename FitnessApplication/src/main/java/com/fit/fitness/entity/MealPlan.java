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
@Table(name = "meal_plans")
public class MealPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mealPlanId; // Unique identifier for the meal plan

	@Column(nullable = false)
	private Long userId; // Identifier of the user creating the plan

	@Column(nullable = false)
	private String mealName; // Name of the meal

	@Column(length = 500) // Limit description length to 500 characters
	private String description; // Detailed description of the meal

	@ElementCollection // To store a list of ingredients
	private List<String> ingredientsList; // List of ingredients used in the meal

	@Column(nullable = false)
	private String nutritionalInformation; // Nutritional breakdown of the meal

	@Column(nullable = false)
	private String frequency; // How often the meal should be consumed (e.g., daily, weekly)

	public Long getMealPlanId() {
		return mealPlanId;
	}

	public void setMealPlanId(Long mealPlanId) {
		this.mealPlanId = mealPlanId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getIngredientsList() {
		return ingredientsList;
	}

	public void setIngredientsList(List<String> ingredientsList) {
		this.ingredientsList = ingredientsList;
	}

	public String getNutritionalInformation() {
		return nutritionalInformation;
	}

	public void setNutritionalInformation(String nutritionalInformation) {
		this.nutritionalInformation = nutritionalInformation;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
