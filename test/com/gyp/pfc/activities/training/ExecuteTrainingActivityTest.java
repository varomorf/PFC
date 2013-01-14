package com.gyp.pfc.activities.training;

import static com.xtremelabs.robolectric.Robolectric.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.exercise.BaseExerciseTest;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;

@RunWith(CustomTestRunner.class)
public class ExecuteTrainingActivityTest extends BaseTrainingTest {

	// Constants -----------------------------------------------------

	private static final int SECS = 60;
	private static final int REPS = 10;
	private static final String EXERCISE2_NAME = "Foo";
	private static final String EXERCISE2_DESC = "Bar";

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
				activity.getText(R.string.repetition) + " 1/10");
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExecuteTrainingActivity();
	}

	// Private -------------------------------------------------------

	private void passTrainingToActivity() {
		training = createTraining(TRAINING_NAME);
		BaseExerciseTest.insertExercise(exerciseDao,
				BaseExerciseTest.EXERCISE_NAME, BaseExerciseTest.EXERCISE_DESC);
		BaseExerciseTest.insertExercise(exerciseDao, EXERCISE2_NAME,
				EXERCISE2_DESC);
		exercise1 = exerciseDao.queryForId(1);
		addExerciseToTraining(training, exercise1, REPS, SECS);
		exercise2 = exerciseDao.queryForId(2);
		addExerciseToTraining(training, exercise2, REPS + 1, SECS + 1);
		intentPassedWithTraining(1);
	}

	// Inner classes -------------------------------------------------
}
