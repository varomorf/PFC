/**
 * 
 */
package com.gyp.pfc.data.domain.nulls;

import com.gyp.pfc.data.domain.Meal;

/**
 * NullObject pattern used for {@link Meal} entities
 * 
 * @author Alvaro
 * 
 */
public class NullMeal extends Meal {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	public static final int ID = -1;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Creates a new {@link NullMeal} with id {@link NullMeal#ID}
	 */
	public NullMeal() {
		setId(ID);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
