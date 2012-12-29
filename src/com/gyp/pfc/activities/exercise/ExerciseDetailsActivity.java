package com.gyp.pfc.activities.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for showing the details of an Exercise entity
 * 
 * @author Alvaro
 * 
 */
public class ExerciseDetailsActivity extends
		OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_details);

		// get intent to get data
		Intent intent = getIntent();
		if (null != intent) {
			// if there's an intent -> get the exercise
			Exercise exercise = (Exercise) intent
					.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private void updateView(Exercise exercise) {
		// get widgets from view
		TextView name = (TextView) findViewById(R.id.exerciseName);
		TextView description = (TextView) findViewById(R.id.exerciseDescription);
		// populate widgets
		name.setText(exercise.getName());
		description.setText(exercise.getDescription());
	}
	// Inner classes -------------------------------------------------

}
