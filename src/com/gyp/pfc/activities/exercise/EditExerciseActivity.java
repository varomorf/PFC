package com.gyp.pfc.activities.exercise;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.Exercise;

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
		View name = findViewById(R.id.exerciseName);
		View description = findViewById(R.id.exerciseDescription);
		exercise.setName(UIUtils.getTextFromUI(name));
		if (assertExercise(exercise)) {
			exercise.setDescription(UIUtils.getTextFromUI(description));
			getHelper().getExerciseDao().update(exercise);
			Toast.makeText(getApplicationContext(), R.string.exerciseEdited,
					Toast.LENGTH_SHORT).show();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void customizeView(View view) {
		// set title
		UIUtils.setTextToUI(view.findViewById(R.id.exerciseDataTitle),
				getText(R.string.editExerciseTitle));
		// set button text
		UIUtils.setTextToUI(view.findViewById(R.id.commitButton),
				getText(R.string.label_edit));

		// get intent to get data
		Intent intent = getIntent();
		if (null != intent) {
			exercise = (Exercise) intent
					.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
			if (null != exercise) {
				// if there's an exercise -> update the view
				updateView(view);
			}
		}
	}

	// Private -------------------------------------------------------
	private void updateView(View view) {
		// populate widgets
		UIUtils.setTextToUI(view.findViewById(R.id.exerciseName),
				exercise.getName());
		UIUtils.setTextToUI(view.findViewById(R.id.exerciseDescription),
				exercise.getDescription());
	}
	// Inner classes -------------------------------------------------
}
