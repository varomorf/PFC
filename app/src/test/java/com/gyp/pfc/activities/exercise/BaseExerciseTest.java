package com.gyp.pfc.activities.exercise;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import android.content.Intent;
import android.widget.Button;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
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
	public static final String EXERCISE_NAME = "Test exercise";
	public static final String EXERCISE_DESC = "Test exercise description";
	public static final int EXERCISE_CALORIES = 100;
	public static final String NEW_NAME = "NEW_NAME";
	public static final String NEW_DESC = "NEW_DESC";
	public static final int NEW_CALORIES = 200;
	public static final String DUPLICATED_NAME = "DUPLICATED_NAME";
	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<Exercise, Integer> dao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		dao = new DatabaseHelper(realActivity).getExerciseDao();
		ExerciseManager.it().setExerciseDao(dao);
	}

	/**
	 * Inserts a new Exercise with the passed arguments into the passed dao
	 * 
	 * @param dao
	 * @param name
	 * @param description
	 */
	public static void insertExercise(RuntimeExceptionDao<Exercise, Integer> dao, String name, String description,
			int calories) {
		Exercise exercise = new Exercise();
		exercise.setName(name);
		exercise.setDescription(description);
		exercise.setBurntCalories(calories);
		dao.create(exercise);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected void insertExercise(String name, String description, int calories) {
		insertExercise(dao, name, description, calories);
	}

	protected void intentPassedWithExercise() {
		intentPassedWithExercise(null);
	}

	protected void intentPassedWithExercise(Exercise exercise) {
		Exercise theExercise = exercise;
		if (theExercise == null) {
			theExercise = new Exercise();
			theExercise.setName(EXERCISE_NAME);
			theExercise.setDescription(EXERCISE_DESC);
			theExercise.setBurntCalories(EXERCISE_CALORIES);
		}
		Intent intent = new Intent();
		intent.putExtra(ExerciseListActivity.SELECTED_EXERCISE, theExercise);
		activity.setIntent(intent);
	}

	protected void enterName(String name) {
		enterText(R.id.exerciseName, name);
	}

	protected void enterDescription(String description) {
		enterText(R.id.exerciseDescription, description);
	}

	protected void enterCalories(int calories) {
		enterText(R.id.exerciseCalories, calories);
	}

	protected void commitButtonIsClicked() {
		Button button = (Button) activity.findViewById(R.id.commitButton);
		clickOn(button);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
