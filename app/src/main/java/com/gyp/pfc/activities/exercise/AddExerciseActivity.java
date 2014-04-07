package com.gyp.pfc.activities.exercise;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
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
		// extract data from widgets
		String name = UIUtils.getTextFromUI(findViewById(R.id.exerciseName));
		String description = UIUtils.getTextFromUI(findViewById(R.id.exerciseDescription));
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
		UIUtils.setTextToUI(view.findViewById(R.id.exerciseDataTitle), getText(R.string.addExerciseTitle));
		// set button text
		UIUtils.setTextToUI(view.findViewById(R.id.commitButton), getText(R.string.button_save));
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
