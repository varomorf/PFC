/**
 * 
 */
package com.gyp.pfc.data.domain.builder;

import com.gyp.pfc.data.domain.meal.MealName;

/**
 * Builder for {@link MealName} entities
 * 
 * @author Alvaro
 * 
 */
public class MealNameBuilder {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * The built meal name
	 */
	private MealName mealName;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link MealNameBuilder} initializing the built meal name
	 */
	public MealNameBuilder() {
		this.mealName = new MealName();
	}

	// Public --------------------------------------------------------

	/**
	 * Sets the built meal name's name
	 * 
	 * @param name
	 *            the name to be set
	 * @return this {@link MealNameBuilder}
	 */
	public MealNameBuilder name(String name) {
		mealName.setName(name);
		return this;
	}

	/**
	 * Sets the built meal name's order
	 * 
	 * @param order
	 *            the order to be set
	 * @return this {@link MealNameBuilder}
	 */
	public MealNameBuilder order(Integer order) {
		mealName.setOrder(order);
		return this;
	}

	/**
	 * Returns the built meal name
	 * 
	 * @return the built meal name
	 */
	public MealName getMealName() {
		return mealName;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
