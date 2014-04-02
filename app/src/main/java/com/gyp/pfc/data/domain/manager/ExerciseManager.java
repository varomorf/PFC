/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Manager for operations with {@link Exercise} entities
 * 
 * @author Alvaro
 * 
 */
public class ExerciseManager {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The DAO to use when creating Exercise entities */
	private RuntimeExceptionDao<Exercise, Integer> exerciseDao;

	// Static --------------------------------------------------------

	/** Singleton instance of the ExerciseManager */
	private static ExerciseManager instance;

	/**
	 * Returns the singleton instance of the ExerciseManager
	 * 
	 * @return the singleton instance of the ExerciseManager
	 */
	public static ExerciseManager getInstance() {
		if (null == instance) {
			instance = new ExerciseManager();
		}
		return instance;
	}

	// Constructors --------------------------------------------------

	/**
	 * Forbid creation of ExerciseManager objects from outside class
	 */
	private ExerciseManager() {

	}

	// Public --------------------------------------------------------

	/**
	 * Sets the exercise DAO to be used
	 * 
	 * @param exerciseDao
	 *            the exercise DAO to be used
	 */
	public void setExerciseDao(
			RuntimeExceptionDao<Exercise, Integer> exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

	/**
	 * Creates a new exercise specifying its name and description
	 * 
	 * @param name
	 *            the name of the new exercise
	 * @param description
	 *            the description of the new exercise
	 * @return the created exercise
	 */
	public Exercise createExercise(String name, String description) {
		Exercise exercise = new Exercise();
		exercise.setName(name);
		exercise.setDescription(description);
		exerciseDao.create(exercise);
		return exercise;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
