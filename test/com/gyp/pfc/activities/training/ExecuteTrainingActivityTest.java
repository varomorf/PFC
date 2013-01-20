package com.gyp.pfc.activities.training;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.exercise.BaseExerciseTest;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.xtremelabs.robolectric.shadows.ShadowCountDownTimer;

@RunWith(CustomTestRunner.class)
public class ExecuteTrainingActivityTest extends BaseTrainingTest {

	// Constants -----------------------------------------------------

	private static final int SECS = 60;
	private static final int REPS = 2;
	private static final String EXERCISE2_NAME = "Exercise 2";
	private static final String EXERCISE2_DESC = "Exercise 2 Description";

	// Attributes ----------------------------------------------------

	private Training training;
	private Exercise exercise1;
	private Exercise exercise2;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
		exercise1 = null;
		exercise2 = null;
	}

	@Test
	public void shouldLoadInitialDataWithIntent() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// WHEN
		// activity is created
		createActivity();
		// THEN
		// training name as title
		assertViewText(R.id.trainingName, training.getName());
		// exercise name is shown
		assertViewText(R.id.exerciseName, exercise1.getName());
		// seconds of first exercise is shown
		assertViewText(R.id.timer, "01:00");
		// reps of first exercise is shown
		assertViewText(R.id.repetitionNumber,
				activity.getText(R.string.repetition) + " 1/2");
		// total exercises are shown
		assertViewText(R.id.exerciseNumberFraction,
				activity.getText(R.string.exercise_label) + " 1/2");
		// button shows resume/pause text
		assertViewText(R.id.actionButton, activity
				.getText(R.string.resumePause).toString());
	}

	@Test
	public void nextButtonShouldWork() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// next button is clicked
		clickOn(activity.findViewById(R.id.nextButton));
		// THEN
		// next exercise data should be loaded
		assertViewText(R.id.exerciseName, exercise2.getName());
	}

	@Test
	public void nextButtonLimitShouldWork() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// next button is clicked 2 times
		clickOn(activity.findViewById(R.id.nextButton));
		clickOn(activity.findViewById(R.id.nextButton));
		// THEN
		// next exercise data should be loaded
		assertViewText(R.id.exerciseName, exercise2.getName());
	}

	@Test
	public void previousButtonShouldWork() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// next button is clicked
		clickOn(activity.findViewById(R.id.nextButton));
		// previous button is clicked
		clickOn(activity.findViewById(R.id.previousButton));
		// THEN
		// next exercise data should be loaded
		assertViewText(R.id.exerciseName, exercise1.getName());
	}

	@Test
	public void previousButtonLimitShouldWork() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// previous button is clicked from start
		clickOn(activity.findViewById(R.id.previousButton));
		// THEN
		// initial exercise data should be loaded
		assertViewText(R.id.exerciseName, exercise1.getName());
	}

	@Test
	public void resumePauseButtonShouldStartTimer() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// resume/pause button is clicked from start
		clickOn(activity.findViewById(R.id.actionButton));
		// THEN
		// timer text should change
		ShadowCountDownTimer shadow = ShadowCountDownTimer.getLast();
		assertTrue(shadow.hasStarted());
		shadow.invokeTick(2000); // fake the passing of time
		assertViewTextIsNot(R.id.timer, "01:00");
	}

	@Test
	public void shouldPassToTheNextRepetitionWhenTimerFinishes() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// resume/pause button is clicked from start
		clickOn(activity.findViewById(R.id.actionButton));
		// time ends
		ShadowCountDownTimer shadow = ShadowCountDownTimer.getLast();
		assertTrue(shadow.hasStarted());
		shadow.invokeFinish(); // fake the finishing of time
		// THEN
		// next repetitions should be loaded
		assertViewText(R.id.repetitionNumber,
				activity.getText(R.string.repetition) + " 2/2");
		// timer time should be reset
		assertViewText(R.id.timer, "01:00");
		// timer should be running
		assertTrue(ShadowCountDownTimer.getLast().hasStarted());
	}

	@Test
	public void shouldPassToTheNextTrainingWhenTimerFinishesAndNoMoreReps() {
		// GIVEN
		// one training is passed via intent to the activity
		passTrainingToActivity();
		// activity is created
		createActivity();
		// WHEN
		// resume/pause button is clicked from start
		clickOn(activity.findViewById(R.id.actionButton));
		// time ends two times
		ShadowCountDownTimer shadow = ShadowCountDownTimer.getLast();
		assertTrue(shadow.hasStarted());
		shadow.invokeFinish(); // fake the finishing of time
		shadow.invokeFinish(); // fake the finishing of time again
		// THEN
		// next exercise data should be loaded
		assertViewText(R.id.exerciseName, exercise2.getName());
		// timer time should be reset
		assertViewText(R.id.timer, "01:01");
		// timer should be running
		assertTrue(ShadowCountDownTimer.getLast().hasStarted());
	}
	
	@Test
	public void shouldPassAutomaticallyToTheNextTrainingWithTimerStopped() {
		// GIVEN
		// one training is passed via intent to the activity with second exercise with no duration
		passTrainingToActivity(0);
		// activity is created
		createActivity();
		// WHEN
		// resume/pause button is clicked from start
		clickOn(activity.findViewById(R.id.actionButton));
		// time ends two times
		ShadowCountDownTimer shadow = ShadowCountDownTimer.getLast();
		assertTrue(shadow.hasStarted());
		shadow.invokeFinish(); // fake the finishing of time
		shadow.invokeFinish(); // fake the finishing of time again
		// THEN
		// next exercise data should be loaded
		assertViewText(R.id.exerciseName, exercise2.getName());
		// timer time should be reset
		assertViewText(R.id.timer, "00:00");
		// timer should be stopped
		assertFalse(ShadowCountDownTimer.getLast().hasStarted());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExecuteTrainingActivity();
	}

	// Private -------------------------------------------------------
	
	private void passTrainingToActivity(){
		passTrainingToActivity(SECS + 1);
	}

	private void passTrainingToActivity(int secondsOfExercise2) {
		training = createTraining(TRAINING_NAME);
		BaseExerciseTest.insertExercise(exerciseDao,
				BaseExerciseTest.EXERCISE_NAME, BaseExerciseTest.EXERCISE_DESC);
		BaseExerciseTest.insertExercise(exerciseDao, EXERCISE2_NAME,
				EXERCISE2_DESC);
		exercise1 = exerciseDao.queryForId(1);
		addExerciseToTraining(training, exercise1, REPS, SECS);
		exercise2 = exerciseDao.queryForId(2);
		addExerciseToTraining(training, exercise2, REPS + 1, secondsOfExercise2);
		trainingDao.refresh(training);
		intentPassedWithTraining(1);
	}

	// Inner classes -------------------------------------------------
}
