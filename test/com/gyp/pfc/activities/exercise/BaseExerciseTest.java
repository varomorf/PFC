package com.gyp.pfc.activities.exercise;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Base class to gather common functionality for Activity testing related to the
 * ExerciseEntity
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseExerciseTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private RuntimeExceptionDao<Exercise, Integer> dao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		dao = new DatabaseHelper(realActivity).getExerciseDao();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected void insertExercise(String name, String description) {
		Exercise exercise = new Exercise();
		exercise.setName(name);
		exercise.setDescription(description);
		dao.create(exercise);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
