package com.gyp.pfc.activities.training;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
public class ExecuteTrainingActivity extends Activity implements
		CountdownTimerListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private Training training;
	private int exerciseIndex;
	private TrainingExercise[] exercises = new TrainingExercise[0];
	private int repetition;

	private CountdownTimer timer;
	private SoundPool soundPool;
	private int whistleShort;
	private int whistleLong;

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
		// reset repetition number
		repetition = 1;
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
		// reset repetition number
		repetition = 1;
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
		// if exercise has no duration -> goto next exercise
		if (exercises[exerciseIndex].getSeconds() == 0) {
			nextButton(null);
		}
		if (!timer.isRunning()) {
			// start the timer if it's not running
			timer.start();
			soundPool.play(whistleLong, 1, 1, 0, 0, 1);
		} else {
			// pause the timer if it's running
			timer.pause();
		}
	}

	@Override
	public void onFinish() {
		// if not last repetition
		if (repetition < exercises[exerciseIndex].getReps()) {
			// increment the repetition number
			repetition++;
			// play notification sound
			soundPool.play(whistleShort, 1, 1, 0, 0, 1);
			// update UI
			updateView();
		} else {
			// if not last exercise
			if (exerciseIndex < exercises.length - 1) {
				// play notification sound
				soundPool.play(whistleShort, 1, 1, 0, 0, 1);
				// when timer ends behave like if next button is pressed
				nextButton(null);
			} else {
				soundPool.play(whistleLong, 1, 1, 0, 0, 1);
				// show completion toast
				Toast.makeText(this, R.string.trainingEnded, Toast.LENGTH_SHORT)
						.show();
				// exit from activity
				finish();
			}
		}
		// and then start again the timer if exercise has duration
		if (exercises[exerciseIndex].getSeconds() > 0) {
			timer.start();
		}
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
		// prepare sounds
		soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 100);
		whistleShort = soundPool.load(this, R.raw.whistle_short, 1);
		whistleLong = soundPool.load(this, R.raw.whistle_long, 1);
		// get data from intent
		Intent intent = getIntent();
		if (null != intent) {
			training = (Training) intent
					.getSerializableExtra(AddTrainingActivity.TRAINING);
			if (null != training) {
				// initialize exercise index
				exerciseIndex = 0;
				// initialize repetition number
				repetition = 1;
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
		int seconds = te.getSeconds();
		timer.setSeconds(seconds);
		setRepetitionNumber(te);
		setExerciseNumberFraction(te);
		if (seconds == 0) {
			UIUtils.setTextToUI(findViewById(R.id.actionButton),
					getText(R.string.done));
		} else {
			UIUtils.setTextToUI(findViewById(R.id.actionButton),
					getText(R.string.resumePause));
		}
	}

	private void setRepetitionNumber(TrainingExercise te) {
		if (te.getSeconds() != 0) {
			setFractionText(R.id.repetitionNumber, R.string.repetition,
					repetition, te.getReps());
		} else {
			UIUtils.setTextToUI(findViewById(R.id.repetitionNumber),
					te.getReps() + " " + getText(R.string.repetitions));
		}
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
