package com.gyp.pfc.activities.training;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Training;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity to add trainings and reference exercises to trainings.
 * 
 * @author Alvaro
 * 
 */
public class AddTrainingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	/**
	 * Callback for the commit button
	 * 
	 * @param view
	 */
	public void commitButton(View view) {
		// get name from view
		String name = UIUtils.getTextFromUI(findViewById(R.id.trainningName));
		// create the new training and set its name
		Training training = new Training();
		training.setName(name);
		if (assertNotBlankName(training)) {
			// persist the training
			getHelper().getTrainingDao().create(training);
			// show toast with OK message
			Toast.makeText(getApplicationContext(), R.string.trainingCreated,
					Toast.LENGTH_SHORT).show();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.training_data);
	}

	// Private -------------------------------------------------------

	private boolean assertNotBlankName(Training training) {
		if (StringUtils.isBlank(training.getName())) {
			// if name blank -> show toast and return false
			Toast.makeText(getApplicationContext(), R.string.trainingNameBlank,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	// Inner classes -------------------------------------------------
}
