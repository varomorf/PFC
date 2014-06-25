/**
 * 
 */
package com.gyp.pfc.activities.historic;

import android.os.Bundle;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.gyp.pfc.data.domain.manager.TrainingManager;

/**
 * Activity for editing a manually entered training historic.
 * 
 * @author Alvaro
 * 
 */
public class EditTrainingHistoricActivity extends AddTrainingHistoricActivity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback method for the ok button
	 * 
	 * @param view
	 *            the ok button
	 */
	public void okButton(View view) {
		Long seconds = (historic.getEnd().getTime() - historic.getStart().getTime()) / 1000;
		// remove previous training
		getHelper().getTrainingDao().delete(historic.getTraining());
		// add new exercise
		Training training = TrainingManager.getInstance().createTraining(exercise.getName(), false);
		TrainingManager.getInstance().addExerciseToTraining(training, exercise, seconds.intValue(), 1);
		historic.setTraining(training);
		getHelper().getTrainingHistoricDao().update(historic);
		// prepare intent for return
		setResult(RESULT_OK);
		finish();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.training_historic_add);
		historic = (TrainingHistoric) getIntent().getExtras().get(SELECTED_HISTORIC);
		UIUtils.setTextToUI(findViewById(R.id.addTrainingHistoricStartingDate),
				TimeUtils.formatDateTime(historic.getStart()));
		UIUtils.setTextToUI(findViewById(R.id.addTrainingHistoricEndingDate),
				TimeUtils.formatDateTime(historic.getEnd()));
		UIUtils.setTextToUI(findViewById(R.id.addTrainingHistoricExerciseName), historic.getTraining().getName());
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
