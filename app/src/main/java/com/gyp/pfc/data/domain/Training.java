package com.gyp.pfc.data.domain;

import java.io.Serializable;

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
	@ForeignCollectionField(eager = true, orderColumnName = "pos")
	private ForeignCollection<TrainingExercise> exercises;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * ORMLite needs a no-arg constructor
	 */
	public Training() {
		// NOOP
	}

	// Public --------------------------------------------------------

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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
