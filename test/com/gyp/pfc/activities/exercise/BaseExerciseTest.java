package com.gyp.pfc.activities.exercise;

import static com.xtremelabs.robolectric.Robolectric.*;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.gyp.pfc.R;
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
	public static final String EXERCISE_NAME = "Test exercise";
	public static final String EXERCISE_DESC = "Test exercise description";
	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<Exercise, Integer> dao;

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

	protected void intentPassedWithExercise() {
		intentPassedWithExercise(null);
	}

	protected void intentPassedWithExercise(Exercise exercise) {
		Exercise theExercise = exercise;
		if (theExercise == null) {
			theExercise = new Exercise();
			theExercise.setName(EXERCISE_NAME);
			theExercise.setDescription(EXERCISE_DESC);
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

	protected void enterText(int id, String text) {
		EditText edit = (EditText) activity.findViewById(id);
		edit.setText(text);
	}

	protected void commitButtonIsClicked() {
		Button button = (Button) activity.findViewById(R.id.commitButton);
		clickOn(button);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
