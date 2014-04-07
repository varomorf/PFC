package com.gyp.pfc.activities.exercise;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivity;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.manager.ExerciseManager;

/**
 * Activity for adding new exercises
 * 
 * @author Alvaro
 * 
 */
public class AddExerciseActivity extends BaseActivity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Call back for the commit button
	 * 
	 * @param view
	 */
	public void commitExercise(View view) {
		// extract data from widgets
		String name = getTextFromUI(R.id.exerciseName);
		String description = getTextFromUI(R.id.exerciseDescription);
		try {
			// on positive case -> create entity, show message and exit
			ExerciseManager.getInstance().createExercise(name, description);
			Toast.makeText(getApplicationContext(), R.string.exerciseCreated, Toast.LENGTH_SHORT).show();
			finish();
		} catch (EntityNameException e) {
			// if name exception -> show toast with error message
			Toast.makeText(getApplicationContext(), e.getExeptionMessageId(), Toast.LENGTH_SHORT).show();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// inflate layout
		View view = getLayoutInflater().inflate(R.layout.exercise_data, null);
		// set the view
		setContentView(view);
		// customize the view
		customizeView(view);
	}

	/**
	 * Customizes the passed view that will be the view set as content view for
	 * the activity
	 * 
	 * @param view
	 *            The view to be customized
	 */
	protected void customizeView(View view) {
		// set title
		setTextToUI(R.id.exerciseDataTitle, R.string.addExerciseTitle);
		// set button text
		setTextToUI(R.id.commitButton, R.string.button_save);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
