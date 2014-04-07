package com.gyp.pfc.activities.exercise;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.manager.ExerciseManager;

/**
 * Activity for editing Exercise entities
 * 
 * @author Alvaro
 * 
 */
public class EditExerciseActivity extends AddExerciseActivity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private Exercise exercise;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void commitExercise(View view) {
		exercise.setName(getTextFromUI(R.id.exerciseName));
		exercise.setDescription(getTextFromUI(R.id.exerciseDescription));
		try {
			ExerciseManager.getInstance().updateExercise(exercise);
			Toast.makeText(getApplicationContext(), R.string.exerciseEdited, Toast.LENGTH_SHORT).show();
			finish();
		} catch (EntityNameException e) {
			// if name exception -> show toast with error message
			Toast.makeText(getApplicationContext(), e.getExeptionMessageId(), Toast.LENGTH_SHORT).show();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void customizeView(View view) {
		// set title
		setTextToUI(R.id.exerciseDataTitle, R.string.editExerciseTitle);
		// set button text
		setTextToUI(R.id.commitButton, R.string.label_edit);

		// get intent to get data
		Intent intent = getIntent();
		if (null != intent) {
			exercise = (Exercise) intent.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
			if (null != exercise) {
				// if there's an exercise -> update the view
				updateView(view);
			}
		}
	}

	// Private -------------------------------------------------------

	private void updateView(View view) {
		// populate widgets
		setTextToUI(R.id.exerciseName, exercise.getName());
		setTextToUI(R.id.exerciseDescription, exercise.getDescription());
	}

	// Inner classes -------------------------------------------------
}
