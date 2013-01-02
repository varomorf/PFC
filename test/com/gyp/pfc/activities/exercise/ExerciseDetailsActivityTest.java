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
		TextView name = (TextView) activity.findViewById(R.id.exerciseName);
		TextView description = (TextView) activity
				.findViewById(R.id.exerciseDescription);
		assertThat(name.getText().toString(), is(EXERCISE_NAME));
		assertThat(description.getText().toString(), is(EXERCISE_DESC));
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExerciseDetailsActivity();
	}

	// Private -------------------------------------------------------
	private void intentPassedWithExercise() {
		intentPassedWithExercise(null);
	}

	private void intentPassedWithExercise(Exercise exercise) {
		Exercise theExercise = exercise;
		if (theExercise == null) {
			theExercise = new Exercise();
			theExercise.setName(EXERCISE_NAME);
			theExercise.setDescription(EXERCISE_DESC);
		}
		Intent intent = new Intent();
		intent.putExtra(ExerciseListActivity.SELECTED_EXERCISE, theExercise);
		activity.setIntent(intent);
	}
	// Inner classes -------------------------------------------------
}
