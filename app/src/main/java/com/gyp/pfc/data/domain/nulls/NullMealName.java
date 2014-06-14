/**
 * 
 */
package com.gyp.pfc.data.domain.nulls;

import com.gyp.pfc.data.domain.MealName;

/**
 * NullObject pattern used for {@link MealName} entities
 * 
 * @author Alvaro
 * 
 */
public class NullMealName extends MealName {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	public static final String NAME = "Null meal";
	public static final int ORDER = -1;

	public static final NullMealName NULL_MEAL_NAME = new NullMealName();

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link NullMealName} specifying its name to
	 * {@link NullMealName#NAME} and order to {@link NullMealName#ORDER}
	 * 
	 * Private so there is only one NullMealName object
	 */
	private NullMealName() {
		setName(NAME);
		setOrder(ORDER);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
