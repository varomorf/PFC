package com.gyp.pfc.activities.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;
import com.gyp.pfc.widgets.CountdownTimer;
import com.gyp.pfc.widgets.CountdownTimer.CountdownTimerListener;

/**
 * Activity for executing a training
 * 
 * @author Alvaro
 * 
 */
public class ExecuteTrainingActivity extends Activity implements CountdownTimerListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private Training training;
	private int exerciseIndex;
	private TrainingExercise[] exercises = new TrainingExercise[0];

	private CountdownTimer timer;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback for the next button
	 * 
	 * @param view
	 */
	public void nextButton(View view) {
		// increment exercise index
		incrementExerciseIndex();
		// update the view
		updateView();
		// stop the timer
		timer.pause();
	}

	/**
	 * Callback for the previous button
	 * 
	 * @param view
	 */
	public void previousButton(View view) {
		// decrement exercise index
		decrementExerciseIndex();
		// update the view
		updateView();
		// stop the timer
		timer.pause();
	}

	/**
	 * Callback for the action button
	 * 
	 * @param view
	 */
	public void actionButton(View view) {
		if (!timer.isRunning()) {
			// start the timer if it's not running
			timer.start();
		}
	}
	
	@Override
	public void onFinish() {
		// when timer ends behave like if next button is pressed
		nextButton(null);
		// and then start again the timer
		timer.start();
	}
	
	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);
		// set activity as timer listener
		timer = (CountdownTimer) findViewById(R.id.timer);
		timer.setListener(this);
		// get data from intent
		Intent intent = getIntent();
		if (null != intent) {
			training = (Training) intent
					.getSerializableExtra(AddTrainingActivity.TRAINING);
			if (null != training) {
				// initialize exercise index
				exerciseIndex = 0;
				// if there's an exercise -> update the view
				updateView();
			}
		}
	}

	// Private -------------------------------------------------------

	private void updateView() {
		// get training exercises
		exercises = training.getExercises().toArray(exercises);
		// fill data
		TrainingExercise te = exercises[exerciseIndex];
		UIUtils.setTextToUI(findViewById(R.id.trainingName), training.getName());
		UIUtils.setTextToUI(findViewById(R.id.exerciseName), te.getExercise()
				.getName());
		timer.setSeconds(te.getSeconds());
		setRepetitionNumber(te, 1);
		setExerciseNumberFraction(te);
	}

	private void setRepetitionNumber(TrainingExercise te, int number) {
		setFractionText(R.id.repetitionNumber, R.string.repetition, number,
				te.getReps());
	}

	private void setExerciseNumberFraction(TrainingExercise te) {
		setFractionText(R.id.exerciseNumberFraction, R.string.exercise_label,
				te.getPos() + 1, exercises.length);
	}

	private void setFractionText(int viewId, int textId, int firstNumber,
			int secondNumber) {
		View view = findViewById(viewId);
		StringBuffer buffer = new StringBuffer(getText(textId));
		buffer.append(" ");
		buffer.append(firstNumber);
		buffer.append("/");
		buffer.append(secondNumber);
		UIUtils.setTextToUI(view, buffer.toString());
	}

	private void incrementExerciseIndex() {
		if (exerciseIndex < exercises.length - 1) {
			exerciseIndex++;
		}
	}

	private void decrementExerciseIndex() {
		if (exerciseIndex > 0) {
			exerciseIndex--;
		}
	}

	// Inner classes -------------------------------------------------
}
