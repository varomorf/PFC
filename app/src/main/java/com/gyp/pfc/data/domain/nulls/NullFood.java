/**
 * 
 */
package com.gyp.pfc.data.domain.nulls;

import com.gyp.pfc.data.domain.Food;

/**
 * NullObject pattern used for {@link Food} entities
 * 
 * @author Alvaro
 * 
 */
public class NullFood extends Food {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	public static final String NAME = "Null Food";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link NullFood} specifying its name to
	 * {@link NullFood#NAME}
	 */
	public NullFood() {
		setName(NAME);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}