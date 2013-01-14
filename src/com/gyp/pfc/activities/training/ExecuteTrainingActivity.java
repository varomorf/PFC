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

/**
 * Activity for executing a training
 * 
 * @author Alvaro
 * 
 */
public class ExecuteTrainingActivity extends Activity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private Training training;
	private TrainingExercise[] exercises = new TrainingExercise[0];
	private CountdownTimer timer;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);
		timer = (CountdownTimer) findViewById(R.id.timer);
		// get data from intent
		Intent intent = getIntent();
		if (null != intent) {
			training = (Training) intent
					.getSerializableExtra(AddTrainingActivity.TRAINING);
			if (null != training) {
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
		TrainingExercise te = exercises[0];
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

	// Inner classes -------------------------------------------------
}
