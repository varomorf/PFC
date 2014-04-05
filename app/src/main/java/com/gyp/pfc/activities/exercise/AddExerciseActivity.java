package com.gyp.pfc.activities.exercise;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for adding new exercises
 * 
 * @author Alvaro
 * 
 */
public class AddExerciseActivity extends OrmLiteBaseActivity<DatabaseHelper> {

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
		View name = findViewById(R.id.exerciseName);
		View description = findViewById(R.id.exerciseDescription);
		Exercise exercise = new Exercise();
		exercise.setName(UIUtils.getTextFromUI(name));
		if (assertExercise(exercise)) {
			exercise.setDescription(UIUtils.getTextFromUI(description));
			getHelper().getExerciseDao().create(exercise);
			Toast.makeText(getApplicationContext(), R.string.exerciseCreated,
					Toast.LENGTH_SHORT).show();
			UIUtils.clearView(name);
			UIUtils.clearView(description);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// inflate layout
		View view = getLayoutInflater().inflate(R.layout.exercise_data, null);
		// customize the view
		customizeView(view);
		// set the view
		setContentView(view);
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
		UIUtils.setTextToUI(view.findViewById(R.id.exerciseDataTitle),
				getText(R.string.addExerciseTitle));
		// set button text
		UIUtils.setTextToUI(view.findViewById(R.id.commitButton),
				getText(R.string.button_save));
	}

	/**
	 * Assert if the passed exercise is valid
	 * 
	 * @param exercise
	 *            The exercise to validate
	 * @return True if valid. False otherwise.
	 */
	protected boolean assertExercise(Exercise exercise) {
		return assertNotBlankName(exercise)
				&& assertNotDuplicatedName(exercise);
	}

	/**
	 * Assert if the passed exercise has duplicated name
	 * 
	 * @param exercise
	 *            The exercise to validate
	 * @return True if valid. False otherwise.
	 */
	protected boolean assertNotDuplicatedName(Exercise exercise) {
		// query an exercise with the same name as the passed exercise
		List<Exercise> tmp = getHelper().getExerciseDao().queryForEq("name",
				exercise.getName());
		// if the list holds exercises -> name is duplicated
		if (!tmp.isEmpty()) {
			// duplicated name -> show toast and return false
			Toast.makeText(getApplicationContext(),
					R.string.exerciseNameDuplicated, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	// Private -------------------------------------------------------

	private boolean assertNotBlankName(Exercise exercise) {
		if (StringUtils.isBlank(exercise.getName())) {
			// if name blank -> show toast and return false
			Toast.makeText(getApplicationContext(), R.string.exerciseNameBlank,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	// Inner classes -------------------------------------------------
}
