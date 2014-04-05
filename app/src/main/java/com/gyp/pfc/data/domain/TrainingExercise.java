package com.gyp.pfc.data.domain;

import static com.gyp.pfc.TimeUtils.minutesFromSeconds;
import static com.gyp.pfc.TimeUtils.restingSecondsFromSeconds;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Entity for relations between Training and Exercise
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class TrainingExercise implements Serializable{

	// Constants -----------------------------------------------------
	
	private static final long serialVersionUID = 5196789157868525622L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private int pos;
	@DatabaseField(canBeNull = false)
	private int seconds;
	@DatabaseField(canBeNull = false)
	private int reps;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Exercise exercise;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Training training;

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

	public int getPos() {
		return pos;
	}

	/**
	 * Sets the pos field if the passed amount is greater than or equal to 0
	 * 
	 * @param pos
	 */
	public void setPos(int pos) {
		if (pos >= 0) {
			this.pos = pos;
		} else {
			throw new IllegalArgumentException(
					"Position must be greater or equal than 0");
		}
	}

	public int getSeconds() {
		return seconds;
	}

	/**
	 * Sets the seconds field if the passed amount is greater than or equal to 0
	 * 
	 * @param seconds
	 */
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

	/**
	 * Sets the reps field if the passed amount is greater than or equal to 1
	 * 
	 * @param reps
	 */
	public void setReps(int reps) {
		if (reps >= 1) {
			this.reps = reps;
		} else {
			throw new IllegalArgumentException(
					"Reps must be greater or equal than 1");
		}
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder(exercise.getName());
		buffer.append(' ');
		buffer.append(reps);
		buffer.append('x');
		buffer.append(minutesFromSeconds(seconds));
		buffer.append(':');
		int restingSeconds = restingSecondsFromSeconds(seconds);
		if(restingSeconds < 10){
			buffer.append('0');
		}
		buffer.append(restingSeconds);
		return buffer.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TrainingExercise) {
			return id == ((TrainingExercise) o).getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
