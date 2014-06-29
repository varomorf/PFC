/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.util.Log;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * Manager for operations with {@link Exercise} entities
 * 
 * @author Alvaro
 * 
 */
public class ExerciseManager {

	// Constants -----------------------------------------------------

	private static final String LOG_TAG = ExerciseManager.class.getName();

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
	public static ExerciseManager it() {
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
	 * Creates a new exercise specifying its name and description. This method assures that the name is not blank
	 * nor repeated.
	 * 
	 * @param name
	 *            the name of the new exercise
	 * @param description
	 *            the description of the new exercise
	 * @param burntCalories
	 *            the amount of calories burnt per hour for this exercise
	 * @return the created exercise
	 * @throws EntityNameException
	 *             if a blank name has been provided
	 */
	public Exercise createExercise(String name, String description, Integer burntCalories)
			throws EntityNameException {
		// create and save the exercise if constraints are valid
		Exercise exercise = new Exercise();
		exercise.setName(name);
		checkNameConstraints(exercise);
		exercise.setDescription(description);
		exercise.setBurntCalories(burntCalories);
		exerciseDao.create(exercise);
		return exercise;
	}

	/**
	 * Updates the passed exercise after checking that its name is not blank nor repeated.
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
		if (!tmp.isEmpty() && !exercise.equals(tmp.get(0))) {
			throw new EntityNameException(R.string.exerciseNameDuplicated);
		}
	}

	/**
	 * Returns a list with all the {@link Exercise} entities on DB
	 * 
	 * @return a list with all the {@link Exercise} entities on DB
	 */
	public List<Exercise> getAllExercises() {
		return exerciseDao.queryForAll();
	}

	/**
	 * It will import the specified {@link Exercise}.
	 * 
	 * This means, that it will save this {@link Exercise} to DB, but if DB already holds an {@link Exercise} with
	 * the same name as the one passed, it will override said exercise with the one passed.
	 * 
	 * @param exercise
	 */
	public void importExercise(Exercise exercise) {
		QueryBuilder<Exercise, Integer> query = exerciseDao.queryBuilder();
		// id will be either given by DB or copied from existing food
		exercise.clearId();
		try {
			query.where().eq("name", exercise.getName());
			List<Exercise> dbExercises = query.query();
			if (!dbExercises.isEmpty()) {
				Exercise dbExercise = dbExercises.get(0);
				exercise.setId(dbExercise.getId());
				exerciseDao.update(exercise);
			} else {
				exerciseDao.create(exercise);
			}
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error while importing exercise with name " + exercise.getName(), e);
		}
	}

	// Inner classes -------------------------------------------------

}
