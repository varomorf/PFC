package com.gyp.pfc.sharing;

import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.food.Food;

/**
 * Enumeration for the names of the files using for sharing entities
 * 
 * @author alfergon
 * 
 */
public enum FileSharingName {

	// Constants -----------------------------------------------------

	FOOD(Food.class.getName(), "foods.yaml"), EXERCISE(Exercise.class.getName(), "exercises.yaml"), TRAINING(
			Training.class.getName(), "trainings.yaml");

	public static final String DATA_DIR_NAME = "PersonalFitnessCoach";

	// Attributes ----------------------------------------------------

	/** The class of the entity */
	private String className;

	/** The filename for this type of entity */
	private String fileName;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link FileSharingName} specifying class name and file name
	 * 
	 * @param className
	 *            the name of the class for the {@link FileSharingName}
	 * @param fileName
	 *            the name of the file
	 */
	private FileSharingName(String className, String fileName) {
		this.className = className;
		this.fileName = fileName;
	}

	// Public --------------------------------------------------------

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the filename for this type of entity
	 */
	public String getFileName() {
		return fileName;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
