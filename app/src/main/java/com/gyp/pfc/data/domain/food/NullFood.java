/**
 * 
 */
package com.gyp.pfc.data.domain.food;


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
	
	public static final NullFood NULL_FOOD = new NullFood();

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------
	
	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link NullFood} specifying its name to
	 * {@link NullFood#NAME}
	 * 
	 * Private so there is only one NullFood object
	 */
	private NullFood() {
		setName(NAME);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
