/**
 * 
 */
package com.gyp.pfc.data.domain;

import java.io.Serializable;

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
public class Portion implements Serializable {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Food food = new NullFood();

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Meal meal = new NullMeal();

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
		this.food = food;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
