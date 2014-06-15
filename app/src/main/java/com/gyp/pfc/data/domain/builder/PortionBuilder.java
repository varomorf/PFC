/**
 * 
 */
package com.gyp.pfc.data.domain.builder;

import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.Portion;

/**
 * Builder for {@link Portion} entities
 * 
 * @author Alvaro
 * 
 */
public class PortionBuilder {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * The built portion
	 */
	private Portion portion;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link PortionBuilder} initializing the built portion
	 */
	public PortionBuilder() {
		this.portion = new Portion();
	}

	// Public --------------------------------------------------------

	/**
	 * Sets the portion's quantity
	 * 
	 * @param quantity
	 *            the quantity to be set to the built portion
	 * @return this {@link PortionBuilder}
	 */
	public PortionBuilder quantity(Integer quantity) {
		portion.setQuantity(quantity);
		return this;
	}

	/**
	 * Sets the portion's food
	 * 
	 * @param food
	 *            the food to be set to the built portion
	 * @return this {@link PortionBuilder}
	 */
	public PortionBuilder food(Food food) {
		portion.setFood(food);
		return this;
	}

	/**
	 * Sets the portion's meal
	 * 
	 * @param meal
	 *            the meal to be set to the built portion
	 * @return this {@link PortionBuilder}
	 */
	public PortionBuilder meal(Meal meal) {
		portion.setMeal(meal);
		return this;
	}

	/**
	 * Returns the built portion
	 * 
	 * @return the built portion
	 */
	public Portion getPortion() {
		return portion;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
