package com.gyp.pfc.activities.training;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.exercise.BaseExerciseTest;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;

@RunWith(CustomTestRunner.class)
public class TrainingListActivityTest extends BaseTrainingTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	private Exercise exercise;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		// one exercise on DB
		BaseExerciseTest.insertExercise(exerciseDao,
				BaseExerciseTest.EXERCISE_NAME, BaseExerciseTest.EXERCISE_DESC);
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new TrainingListActivity();
	}

	// Private -------------------------------------------------------
	private void createTraining(String name, int reps, int seconds) {
		// prepare new training
		Training training = new Training();
		training.setName(name);
		trainingDao.create(training);
		// add exercise to training with passed seconds
		TrainingExercise te = new TrainingExercise();
		te.setTraining(training);
		te.setExercise(exercise);
		te.setSeconds(seconds);
		te.setReps(reps);
		// save entities
		trainingExerciseDao.create(te);
	}

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
