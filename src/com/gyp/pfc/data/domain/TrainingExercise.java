package com.gyp.pfc.data.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Entity for relations between Training and Exercise
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class TrainingExercise {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private int pos;
	@DatabaseField(canBeNull = false, foreign = true)
	private Exercise exercise;
	@DatabaseField(canBeNull = false)
	private int seconds;
	@DatabaseField(canBeNull = false)
	private int reps;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * ORMLite needs a no-arg constructor
	 */
	public TrainingExercise() {
		// NOOP
	}

	// Public --------------------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		if (pos >= 1) {
			this.pos = pos;
		}else {
			throw new IllegalArgumentException(
					"Position must be greater or equal than 1");
		}
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		if (seconds >= 0) {
			this.seconds = seconds;
		} else {
			throw new IllegalArgumentException(
					"Seconds must be greater or equal than 0");
		}
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		if (reps >= 1) {
			this.reps = reps;
		} else {
			throw new IllegalArgumentException(
					"Reps must be greater or equal than 1");
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
