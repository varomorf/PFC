package com.gyp.pfc.activities.exercise;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;

/**
 * Tests for the ExerciseDetailsActivity
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class ExerciseDetailsActivityTest extends BaseExerciseTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldShowAllInfoOfPassedExercise() {
		// GIVEN
		intentPassedWithExercise();
		// WHEN
		createActivity();
		// THEN
		assertDataFilled(EXERCISE_NAME, EXERCISE_DESC);
	}

	@Test
	public void shouldHaveMenuForCrudOptions() {
		// GIVEN
		intentPassedWithExercise();
		// WHEN
		createActivity();
		activity.pressMenuKey();
		// THEN
		TestMenu menu = TestMenu.getLastMenu();
		assertCRUDMenu(menu);
	}

	@Test
	public void shouldDeleteExercises() {
		// GIVEN
		// exercise on DB
		insertExercise(EXERCISE_NAME, EXERCISE_DESC);
		// pass DB exercise to activity
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// WHEN
		// activity is shown
		createActivity();
		// menu key is pressed
		activity.pressMenuKey();
		// delete option is clicked
		TestMenu menu = TestMenu.getLastMenu();
		menu.clickOn(0);
		// THEN
		// toast with deletion message is shown
		assertToastText(R.string.exerciseDeleted);
		// exercise no longer on DB
		assertNull(dao.queryForId(1));
		// activity is finished
		assertTrue(activity.isFinishing());
	}

	@Test
	public void shouldEditExercise() {
		// GIVEN
		// exercise on DB
		insertExercise(EXERCISE_NAME, EXERCISE_DESC);
		// pass DB exercise to activity
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		// WHEN
		// activity is shown
		createActivity();
		// menu key is pressed
		activity.pressMenuKey();
		// edit option is clicked
		TestMenu menu = TestMenu.getLastMenu();
		menu.clickOn(1);
		// THEN
		// next activity is EditExerciseActivity passing exercise
		Intent nextIntent = assertAndReturnNextActivity(EditExerciseActivity.class);
		exercise = (Exercise) nextIntent
				.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
		assertThat(exercise.getName(), is(dao.queryForId(1).getName()));
	}

	@Test
	public void shouldRefreshViewWhenResumed() {
		// GIVEN
		// activity with exercise
		// exercise on DB
		insertExercise(EXERCISE_NAME, EXERCISE_DESC);
		// pass DB exercise to activity
		Exercise exercise = dao.queryForId(1);
		intentPassedWithExercise(exercise);
		createActivity();
		// exercise changed
		exercise.setName(NEW_NAME);
		exercise.setDescription(NEW_DESC);
		dao.update(exercise);
		// WHEN
		// activity resumed
		activity.callOnResume();
		// THEN
		// new data of exercise is loaded
		assertDataFilled(NEW_NAME,NEW_DESC);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExerciseDetailsActivity();
	}

	// Private -------------------------------------------------------
	private void assertDataFilled(String expectedName, String expectedDesc) {
		TextView name = (TextView) activity.findViewById(R.id.exerciseName);
		TextView description = (TextView) activity
				.findViewById(R.id.exerciseDescription);
		assertThat(name.getText().toString(), is(expectedName));
		assertThat(description.getText().toString(), is(expectedDesc));
	}
	// Inner classes -------------------------------------------------
}
