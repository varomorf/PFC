/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.exception.EntityNameException;
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
	public void setExerciseDao(RuntimeExceptionDao<Exercise, Integer> exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

	/**
	 * Creates a new exercise specifying its name and description. This method
	 * assures that the name is not blank nor repeated.
	 * 
	 * @param name
	 *            the name of the new exercise
	 * @param description
	 *            the description of the new exercise
	 * @return the created exercise
	 * @throws EntityNameException
	 *             if a blank name has been provided
	 */
	public Exercise createExercise(String name, String description) throws EntityNameException {
		// create and save the exercise if constraints are valid
		Exercise exercise = new Exercise();
		exercise.setName(name);
		checkNameConstraints(exercise);
		exercise.setDescription(description);
		exerciseDao.create(exercise);
		return exercise;
	}

	/**
	 * Updates the passed exercise after checking that its name is not blank nor
	 * repeated.
	 * 
	 * @param exercise
	 *            the exercise to be updated
	 * @return <code>true</code> if the exercise has been correctly updated
	 * @throws DuplicatedNameException
	 *             if a blank name has been provided
	 */
	public boolean updateExercise(Exercise exercise) throws EntityNameException {
		// update exercise if constraints are valid
		checkNameConstraints(exercise);
		exerciseDao.update(exercise);
		return true;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Checks that the passed name is valid for the exercise name constraints
	 * 
	 * @param exercise
	 *            the exercise to be checked
	 * @throws EntityNameException
	 *             if a blank name has been provided
	 */
	private void checkNameConstraints(Exercise exercise) throws EntityNameException {
		// blank names not allowed
		if (StringUtils.isBlank(exercise.getName())) {
			throw new EntityNameException(R.string.exerciseNameBlank);
		}
		List<Exercise> tmp = exerciseDao.queryForEq("name", exercise.getName());
		// if there are exercises with specified name -> name is duplicated
		if (!tmp.isEmpty() && tmp.get(0).getId() != exercise.getId()) {
			throw new EntityNameException(R.string.exerciseNameDuplicated);
		}
	}

	// Inner classes -------------------------------------------------

}
