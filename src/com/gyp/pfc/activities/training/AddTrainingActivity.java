package com.gyp.pfc.activities.training;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.AddExerciseDialog;
import com.gyp.pfc.AddExerciseDialog.AddExerciseDialogListener;
import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity to add trainings and reference exercises to trainings.
 * 
 * @author Alvaro
 * 
 */
public class AddTrainingActivity extends OrmLiteBaseActivity<DatabaseHelper>
		implements AddExerciseDialogListener {

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
			Toast.makeText(this, R.string.trainingCreated, Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Callback method for the Add button
	 * 
	 * @param view
	 */
	public void addExercise(View view) {
		// get list of exercises
		List<Exercise> exercises = getHelper().getExerciseDao().queryForAll();
		if (exercises.size() > 0) {
			// create dialog passing this activity as listener
			Dialog dialog = new AddExerciseDialog(this, this, exercises);
			// show the dialog
			dialog.show();
		} else {
			//no exercises -> show toast with error message
			Toast.makeText(this, R.string.emptyExercisesList, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Extracts the data from the closing dialog and creates a TrainingExercise
	 * entity with it
	 */
	public void onDialogClosing(AddExerciseDialog dialog) {

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
			Toast.makeText(this, R.string.trainingNameBlank, Toast.LENGTH_SHORT)
					.show();
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
			Toast.makeText(this, R.string.trainingNameDuplicated,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	// Inner classes -------------------------------------------------
}
