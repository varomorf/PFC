package com.gyp.pfc.activities.training;

import java.util.List;

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

	private Training training;

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
		if (training == null) {
			// create the new training
			training = new Training();
		}
		// set training name
		training.setName(name);
		if (assertTraining(training)) {
			// persist the training
			getHelper().getTrainingDao().createOrUpdate(training);
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

	private boolean assertTraining(Training training) {
		return assertNotBlankName(training)
				&& assertNotDuplicatedName(training);
	}

	private boolean assertNotBlankName(Training training) {
		if (StringUtils.isBlank(training.getName())) {
			// if name blank -> show toast and return false
			Toast.makeText(getApplicationContext(), R.string.trainingNameBlank,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private boolean assertNotDuplicatedName(Training training) {
		// query an exercise with the same name as the passed exercise
		List<Training> tmp = getHelper().getTrainingDao().queryForEq("name",
				training.getName());
		// if the list holds exercises -> name is duplicated
		if (tmp.size() != 0) {
			// duplicated name -> show toast and return false
			Toast.makeText(getApplicationContext(),
					R.string.trainingNameDuplicated, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	// Inner classes -------------------------------------------------
}
