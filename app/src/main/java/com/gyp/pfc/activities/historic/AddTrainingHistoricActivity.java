/**
 * 
 */
package com.gyp.pfc.activities.historic;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.activities.exercise.ExerciseListActivity;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for adding manually a training historic.
 * 
 * The user will use this activity for entering for example an hour of running,
 * 5 minutes of push ups, or any training consisting of only one exercise (and
 * so not using the executable trainings).
 * 
 * @author Alvaro
 * 
 */
public class AddTrainingHistoricActivity extends OrmLiteBaseActivity<DatabaseHelper> implements ExerciseConstants,
		OnDateSetListener, OnTimeSetListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The historic that's being created */
	protected TrainingHistoric historic = new TrainingHistoric();

	/** The selected exercise */
	protected Exercise exercise;

	/** Flag for selecting starting or ending dates */
	private boolean starting;

	/** Helper to be used */
	private BaseActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback method for the find exercise button
	 * 
	 * @param view
	 *            the find exercise button
	 */
	public void findExercise(View view) {
		Intent findExercise = new Intent(this, ExerciseListActivity.class);
		findExercise.putExtra(RETURN_EXERCISE, true);
		startActivityForResult(findExercise, SELECT_EXERCISE);
	}

	/**
	 * Callback method for the startingDate button
	 * 
	 * @param view
	 *            the startingDate button
	 */
	public void startingDate(View view) {
		starting = true;
		h.showDatePickerDialogForToday();
	}

	/**
	 * Callback method for the endingDate button
	 * 
	 * @param view
	 *            the endingDate button
	 */
	public void endingDate(View view) {
		starting = false;
		h.showDatePickerDialogForToday();
	}

	/**
	 * Callback method for the ok button
	 * 
	 * @param view
	 *            the ok button
	 */
	public void okButton(View view) {
		Long seconds = (historic.getEnd().getTime() - historic.getStart().getTime()) / 1000;
		Training training = TrainingManager.getInstance().createTraining(exercise.getName(), false);
		TrainingManager.getInstance().addExerciseToTraining(training, exercise, seconds.intValue(), 1);
		historic.setTraining(training);
		getHelper().getTrainingHistoricDao().create(historic);
		finish();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Date date = h.getDateFromPicker(year, monthOfYear, dayOfMonth);
		if (starting) {
			historic.setStart(date);
		} else {
			historic.setEnd(date);
		}
		showTimePickerDialogForNow();
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Date date = null;
		if (starting) {
			date = historic.getStart();
		} else {
			date = historic.getEnd();
		}
		date = DateUtils.setHours(date, hourOfDay);
		date = DateUtils.setMinutes(date, minute);
		if (starting) {
			historic.setStart(date);
			UIUtils.setTextToUI(findViewById(R.id.addTrainingHistoricStartingDate), TimeUtils.formatDateTime(date));
		} else {
			historic.setEnd(date);
			UIUtils.setTextToUI(findViewById(R.id.addTrainingHistoricEndingDate), TimeUtils.formatDateTime(date));
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new BaseActivityHelper(this);

		setContentView(R.layout.training_historic_add);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		exercise = (Exercise) data.getExtras().get(SELECTED_EXERCISE);
		UIUtils.setTextToUI(findViewById(R.id.addTrainingHistoricExerciseName), exercise.getName());
	}

	/**
	 * Shows a {@link TimePickerDialog} for the current time
	 */
	protected void showTimePickerDialogForNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		new TimePickerDialog(this, this, hour, minutes, true).show();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
