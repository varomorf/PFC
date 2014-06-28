package com.gyp.pfc.sharing;

import com.gyp.pfc.data.domain.exercise.Training;

/**
 * Positions on yaml node for {@link Training} properties
 * 
 * @author alfergon
 * 
 */
public enum TrainingYamlPropertyPosition {

	// Constants -----------------------------------------------------

	ID(2), NAME(3), EXERCISES(1), EXECUTABLE(0);

	// Attributes ----------------------------------------------------

	/** The position */
	public final int pos;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	private TrainingYamlPropertyPosition(int pos) {
		this.pos = pos;
	}

	// Public --------------------------------------------------------

	/**
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
