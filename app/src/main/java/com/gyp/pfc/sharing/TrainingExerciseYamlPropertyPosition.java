package com.gyp.pfc.sharing;

import com.gyp.pfc.data.domain.exercise.TrainingExercise;

/**
 * Positions on yaml node for {@link TrainingExercise} properties
 * 
 * @author alfergon
 * 
 */
public enum TrainingExerciseYamlPropertyPosition {

	// Constants -----------------------------------------------------

	EXERCISE(0), POS(2), REPS(3), SECONDS(4);

	// Attributes ----------------------------------------------------

	/** The position */
	public final int pos;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	private TrainingExerciseYamlPropertyPosition(int pos) {
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
