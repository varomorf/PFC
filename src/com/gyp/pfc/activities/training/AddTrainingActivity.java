package com.gyp.pfc.activities.training;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gyp.pfc.AddExerciseDialog;
import com.gyp.pfc.AddExerciseDialog.AddExerciseDialogListener;
import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;
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
	private static final int SECONDS_PER_MINUTE = 60;
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
		// assure training
		getTraining();
		if (assertTraining()) {
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
		// assure training
		getTraining();
		if (assertTraining()) {
			// get list of exercises
			List<Exercise> exercises = getHelper().getExerciseDao()
					.queryForAll();
			if (exercises.size() > 0) {
				// create dialog passing this activity as listener
				Dialog dialog = new AddExerciseDialog(this, this, exercises);
				// show the dialog
				dialog.show();
			} else {
				// no exercises -> show toast with error message
				Toast.makeText(this, R.string.emptyExercisesList,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Extracts the data from the closing dialog and creates a TrainingExercise
	 * entity with it
	 */
	public void onDialogClosing(AddExerciseDialog dialog) {
		// save training so it can be assigned on the TrainingExercise
		getHelper().getTrainingDao().createOrUpdate(training);
		// create new TrainingExercise entity
		TrainingExercise te = createTrainingExercise(dialog);
		// save TrainingExercise to DB
		getHelper().getTrainingExerciseDao().create(te);
		// inflate a training_exercise view
		View view = getLayoutInflater().inflate(R.layout.training_exercise_item, null);
		// set title
		UIUtils.setTextToUI(view.findViewById(R.id.title), te.getExercise().getName());
		// add new view to exercise list
		LinearLayout exercises = (LinearLayout) findViewById(R.id.exercisesLayout);
		exercises.addView(view);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.training_data);
	}

	// Private -------------------------------------------------------

	private boolean assertTraining() {
		return assertNotBlankName() && assertNotDuplicatedName();
	}

	private boolean assertNotBlankName() {
		if (StringUtils.isBlank(training.getName())) {
			// if name blank -> show toast and return false
			Toast.makeText(this, R.string.trainingNameBlank, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private boolean assertNotDuplicatedName() {
		// query an training with the same name as the passed training
		List<Training> tmp = getHelper().getTrainingDao().queryForEq("name",
				training.getName());
		// if the list holds trainings -> name is duplicated
		if (tmp.size() != 0) {
			// if only returned training has same id than passed is ok
			if (tmp.size() == 1 && tmp.get(0).getId() == training.getId()) {
				return true;
			}
			// duplicated name -> show toast and return false
			Toast.makeText(this, R.string.trainingNameDuplicated,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private Training getTraining() {
		if (training == null) {
			// create the new training
			training = new Training();
		}
		// get name from view
		String name = UIUtils.getTextFromUI(findViewById(R.id.trainningName));
		// set training name
		training.setName(name);
		return training;
	}

	private TrainingExercise createTrainingExercise(AddExerciseDialog dialog) {
		// create new TrainingExercise entity
		TrainingExercise te = new TrainingExercise();
		// assure training
		training = getTraining();
		// set training
		te.setTraining(training);
		// set exercise from spinner
		Spinner spinner = (Spinner) dialog.findViewById(R.id.exerciseSpinner);
		te.setExercise((Exercise) spinner.getSelectedItem());
		// set repetitions
		String repetitions = UIUtils.getTextFromUI(dialog
				.findViewById(R.id.repetitions));
		te.setReps(Integer.parseInt(repetitions));
		// set seconds from minutes
		String minutes = UIUtils.getTextFromUI(dialog
				.findViewById(R.id.minutes));
		te.setSeconds(Integer.parseInt(minutes) * SECONDS_PER_MINUTE);
		// add the seconds
		String seconds = UIUtils.getTextFromUI(dialog
				.findViewById(R.id.seconds));
		te.setSeconds(te.getSeconds() + Integer.parseInt(seconds));
		// get number of exercises entered
		int num = ((LinearLayout) findViewById(R.id.exercisesLayout))
				.getChildCount();
		// set pos to num
		te.setPos(num);
		return te;
	}
	// Inner classes -------------------------------------------------
}
