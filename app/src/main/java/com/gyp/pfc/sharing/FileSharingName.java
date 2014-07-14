package com.gyp.pfc.sharing;

import com.gyp.pfc.data.domain.biometric.UserData;
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

	FOOD(Food.class.getName(), "foods.yaml", 0.186d), EXERCISE(Exercise.class.getName(), "exercises.yaml", 0.126d), TRAINING(
			Training.class.getName(), "trainings.yaml", 0.656d), USER_DATA(UserData.class.getName(), "userData",
			0d);

	public static final String DATA_DIR_NAME = "PersonalFitnessCoach";

	// Attributes ----------------------------------------------------

	/** The class of the entity */
	private String className;

	/** The filename for this type of entity */
	private String fileName;

	/**
	 * The amount of Kb per entity that the resulting file will have. This values has been calculated via examples
	 * and should be revised.
	 */
	private Double entityKbSizeRatio;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link FileSharingName} specifying class name and file name
	 * 
	 * @param className
	 *            the name of the class for the {@link FileSharingName}
	 * @param fileName
	 *            the name of the file
	 * @param entityKbSizeRatio
	 *            the amount of Kb per entity on the resulting file
	 */
	private FileSharingName(String className, String fileName, Double entityKbSizeRatio) {
		this.className = className;
		this.fileName = fileName;
		this.entityKbSizeRatio = entityKbSizeRatio;
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

	/**
	 * @return the entityKbSizeRatio
	 */
	public Double getEntityKbSizeRatio() {
		return entityKbSizeRatio;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
