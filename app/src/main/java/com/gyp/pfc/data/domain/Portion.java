/**
 * 
 */
package com.gyp.pfc.data.domain;

import java.io.Serializable;

import com.gyp.pfc.data.domain.nulls.NullFood;
import com.gyp.pfc.data.domain.nulls.NullMeal;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Defines a portion of food consumed.
 * 
 * That is: a quantity in grams of that food for a certain meal.
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class Portion implements Serializable, NutritionalInformationProvider {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Food food = NullFood.NULL_FOOD;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Meal meal = NullMeal.NULL_MEAL;

	/** The amount in grams of the food */
	@DatabaseField
	private Integer quantity = 0;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public String toString() {
		return new StringBuilder().append(id).append(" - ").append(quantity).append("gr. of ")
				.append(food.getName()).append(" for meal ").append(meal.getId()).toString();
	}

	@Override
	public Double getCalories() {
		return calculateAmountWithQuantity(food.getCalories());
	}

	@Override
	public Double getCarbs() {
		return calculateAmountWithQuantity(food.getCarbs());
	}

	@Override
	public Double getProtein() {
		return calculateAmountWithQuantity(food.getProtein());
	}

	@Override
	public Double getFats() {
		return calculateAmountWithQuantity(food.getFats());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food != null ? food : NullFood.NULL_FOOD;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal != null ? meal : NullMeal.NULL_MEAL;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	
	/**
	 * Calculates the correct amount with the portion's quantity
	 * @param amount amount to calculate
	 * @return the calculated amount
	 */
	private Double calculateAmountWithQuantity(Double amount){
		return (amount * quantity) / 100d;
	}

	// Inner classes -------------------------------------------------

}
