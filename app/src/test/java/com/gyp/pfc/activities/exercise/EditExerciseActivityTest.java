package com.gyp.pfc.activities.exercise;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.exercise.Exercise;

@RunWith(CustomTestRunner.class)
public class EditExerciseActivityTest extends BaseExerciseTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		insertExercise(EXERCISE_NAME, EXERCISE_DESC, EXERCISE_CALORIES);
	}

	@Test
	public void shouldFillData() {
		// GIVEN
		// passed exercise
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// WHEN
		// activity shown
		createActivity();
		// THEN
		// data is filled in form
		assertViewText(R.id.exerciseName, exercise.getName());
		assertViewText(R.id.exerciseDescription, exercise.getDescription());
		assertViewText(R.id.exerciseCalories, exercise.getBurntCalories());
	}

	@Test
	public void shouldEditExercise() {
		// GIVEN
		// passed exercise
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// activity shown
		createActivity();
		// WHEN
		// different and correct values are entered
		enterName(NEW_NAME);
		enterDescription(NEW_DESC);
		enterCalories(NEW_CALORIES);
		// edit button is clicked
		commitButtonIsClicked();
		// THEN
		// assert toast is shown with error
		assertToastText(R.string.exerciseEdited);
		// exercise data is changed
		exercise = dao.queryForId(1);
		assertThat(exercise.getName(), is(NEW_NAME));
		assertThat(exercise.getDescription(), is(NEW_DESC));
		assertThat(exercise.getBurntCalories(), is(NEW_CALORIES));
		// activity is finished
		assertTrue(activity.isFinishing());
	}

	@Test
	public void shouldNotAllowEmptyName() {
		// GIVEN
		// passed exercise
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// activity shown
		createActivity();
		// WHEN
		// empty name is entered
		enterName("");
		// edit button is clicked
		commitButtonIsClicked();
		// THEN
		// assert toast is shown with error
		assertToastText(R.string.exerciseNameBlank);
		// exercise is not changed
		exerciseIsNotChanged(exercise);
	}

	@Test
	public void shouldNotAllowDuplicatedName() {
		// GIVEN
		// passed exercise
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// activity shown
		createActivity();
		// exercise with name to duplicate
		insertExercise(DUPLICATED_NAME, EXERCISE_DESC, EXERCISE_CALORIES);
		// WHEN
		// duplicated name is entered
		enterName(DUPLICATED_NAME);
		// edit button is clicked
		commitButtonIsClicked();
		// THEN
		// assert toast is shown with error
		assertToastText(R.string.exerciseNameDuplicated);
		// exercise is not changed
		exerciseIsNotChanged(exercise);
	}

	@Test
	public void shouldEditWithSameName() {
		// GIVEN
		// passed exercise
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// activity shown
		createActivity();
		// WHEN
		// change description
		enterDescription(NEW_DESC);
		// edit button is clicked
		commitButtonIsClicked();
		// THEN
		// assert toast is shown with error
		assertToastText(R.string.exerciseEdited);
		// exercise data is changed
		exercise = dao.queryForId(1);
		assertThat(exercise.getDescription(), is(NEW_DESC));
		// activity is finished
		assertTrue(activity.isFinishing());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditExerciseActivity();
	}

	// Private -------------------------------------------------------
	private void exerciseIsNotChanged(Exercise exercise) {
		assertThat(exercise.getName(), is(EXERCISE_NAME));
		assertThat(exercise.getDescription(), is(EXERCISE_DESC));
	}
	// Inner classes -------------------------------------------------
}
