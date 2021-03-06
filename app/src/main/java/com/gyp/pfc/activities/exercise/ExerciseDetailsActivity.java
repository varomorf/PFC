package com.gyp.pfc.activities.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for showing the details of an Exercise entity
 * 
 * @author Alvaro
 * 
 */
public class ExerciseDetailsActivity extends OrmLiteBaseActivity<DatabaseHelper>  {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private Exercise exercise;
	
	/** Helper to be used */
	private BaseActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new BaseActivityHelper(this);
		setContentView(R.layout.exercise_details);

		// get intent to get data
		Intent intent = getIntent();
		if (null != intent) {
			exercise = (Exercise) intent.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
			if (null != exercise) {
				// if there's an exercise -> update the view
				updateView(exercise);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// inflate CRUD menu
		getMenuInflater().inflate(R.menu.crud_context_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			deleteExercise();
			return true;
		case R.id.edit:
			editExercise();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onResume() {
		super.onResume();
		// get exercise from DB
		exercise = getHelper().getExerciseDao().queryForId(exercise.getId());
		// fill the form
		updateView(exercise);
	}

	// Private -------------------------------------------------------
	private void updateView(Exercise exercise) {
		// populate widgets
		h.setTextToUI(R.id.exerciseName, exercise.getName());
		h.setTextToUI(R.id.exerciseDescription, exercise.getDescription());
		h.setTextToUI(R.id.exerciseCalories, Integer.toString(exercise.getBurntCalories()));
	}

	private void deleteExercise() {
		// delete exercise on DB
		getHelper().getExerciseDao().delete(exercise);
		// show deletion message
		Toast.makeText(getApplicationContext(), R.string.exerciseDeleted, Toast.LENGTH_SHORT).show();
		// finish activity
		finish();
	}

	private void editExercise() {
		// prepare intent for edition
		Intent intent = new Intent(getApplicationContext(), EditExerciseActivity.class);
		// pass selected exercise
		intent.putExtra(ExerciseListActivity.SELECTED_EXERCISE, exercise);
		// launch activity
		startActivity(intent);
	}
	// Inner classes -------------------------------------------------

}
