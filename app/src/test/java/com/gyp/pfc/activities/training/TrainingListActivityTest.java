package com.gyp.pfc.activities.training;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.exercise.BaseExerciseTest;
import com.gyp.pfc.data.domain.Training;

@RunWith(CustomTestRunner.class)
public class TrainingListActivityTest extends BaseTrainingTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		// one exercise on DB
		BaseExerciseTest.insertExercise(exerciseDao, BaseExerciseTest.EXERCISE_NAME,
				BaseExerciseTest.EXERCISE_DESC);
		exercise = exerciseDao.queryForId(1);
	}

	@Test
	public void shouldListAllTrainingsWithTotalTime() {
		// GIVEN
		// trainings with exercises on DB
		trainingsWithWxercisesOnDB();
		// WHEN
		// activity is shown
		createActivity();
		// THEN
		// all trainings are listed with their total duration
		assertTitleOfChild(0, "foo");
		assertTextOfListChild(0, R.id.time, "01:40");
		assertTitleOfChild(1, "bar");
		assertTextOfListChild(1, R.id.time, "06:40");
		assertTitleOfChild(2, "xyz");
		assertTextOfListChild(2, R.id.time, "15:00");
	}

	@Test
	public void shouldDeleteTrainings() {
		// GIVEN
		// activity with items
		activityWithItems();
		// WHEN
		// delete button pressed on item 1
		clickOnListItemButton(1, R.id.deleteButton);
		// THEN
		// toast with message is shown
		assertToastText(R.string.trainingDeleted);
		// item 1 has text of training 3
		assertTitleOfChild(1, trainingDao.queryForId(3).getName());
		// training 1 no longer on DB
		assertNull(trainingDao.queryForId(2));
		// trainingExercises of training 1 no longer on DB
		assertThat(trainingExerciseDao.queryForEq("training_id", 2).size(), is(0));
	}

	@Test
	public void shouldEditTrainings() {
		// GIVEN
		// activity with items
		activityWithItems();
		// WHEN
		// delete button pressed on item 1
		clickOnListItemButton(1, R.id.editButton);
		// THEN
		// add training activity is shown passing the selected training
		Intent next = assertAndReturnNextActivity(AddTrainingActivity.class);
		Training training = (Training) next.getSerializableExtra(AddTrainingActivity.TRAINING);
		assertThat(training, is(trainingDao.queryForId(2)));
	}

	@Test
	public void shouldRefreshListOnResume() {
		// GIVEN
		// activity with items
		activityWithItems();
		// WHEN
		// one training is deleted
		Training training = trainingDao.queryForId(2);
		trainingExerciseDao.delete(training.getExercises());
		trainingDao.delete(training);
		// activity is resumed
		activity.callOnResume();
		// THEN
		// list shown only resting trainings
		assertTitleOfChild(0, "foo");
		assertTextOfListChild(0, R.id.time, "01:40");
		assertTitleOfChild(1, "xyz");
		assertTextOfListChild(1, R.id.time, "15:00");
	}

	@Test
	public void shouldExecuteTrainings() {
		// GIVEN
		// activity with items
		activityWithItems();
		// WHEN
		// execute button pressed on item 1
		clickOnListItemButton(1, R.id.playButton);
		// THEN
		// add training activity is shown passing the selected training
		Intent next = assertAndReturnNextActivity(ExecuteTrainingActivity.class);
		Training training = (Training) next.getSerializableExtra(AddTrainingActivity.TRAINING);
		assertThat(training, is(trainingDao.queryForId(2)));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new TrainingListActivity();
	}

	// Private -------------------------------------------------------

	private void trainingsWithWxercisesOnDB() {
		createTraining("foo", 1, 100);
		createTraining("bar", 2, 200);
		createTraining("xyz", 3, 300);
	}

	private void activityWithItems() {
		trainingsWithWxercisesOnDB();
		createActivity();
	}

	// Inner classes -------------------------------------------------
}
