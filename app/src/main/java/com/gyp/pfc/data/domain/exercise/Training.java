package com.gyp.pfc.data.domain.exercise;

import java.io.Serializable;

import com.gyp.pfc.data.domain.api.NullForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Training entity
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class Training implements Serializable {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 2358769454877224282L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String name;

	/**
	 * Whether this training can be executed or not
	 */
	@DatabaseField
	private boolean executable = true;

	@ForeignCollectionField(eager = true, orderColumnName = "pos")
	private ForeignCollection<TrainingExercise> exercises = new NullForeignCollection<TrainingExercise, Integer>();

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * ORMLite needs a no-arg constructor
	 */
	public Training() {
		// NOOP
	}

	// Public --------------------------------------------------------

	/**
	 * Adds the passed {@link TrainingExercise} to the exercises collection and
	 * also sets this {@link Training} as the {@link Training} of the
	 * {@link TrainingExercise}
	 * 
	 * @param te
	 *            the {@link TrainingExercise} to be added
	 */
	public void addTrainingExercise(TrainingExercise te) {
		te.setTraining(this);
		exercises.add(te);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ForeignCollection<TrainingExercise> getExercises() {
		return exercises;
	}

	public void setExercises(ForeignCollection<TrainingExercise> exercises) {
		this.exercises = exercises;
	}

	public boolean isExecutable() {
		return executable;
	}

	public void setExecutable(boolean executable) {
		this.executable = executable;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Training) {
			return ((Training) o).getId() == id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * Returns the amount of calories burnt in this training by adding up all
	 * the burnt calories of the exercises
	 * 
	 * @return the amount of calories burnt in this training
	 */
	public int getBurntCalories() {
		Double calories = 0d;
		for (TrainingExercise exercise : exercises) {
			int reps = exercise.getReps();
			int secs = exercise.getSeconds();
			int cals = exercise.getExercise().getBurntCalories();
			calories += reps * ((cals / 3600d) * secs);
		}
		return calories.intValue();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
