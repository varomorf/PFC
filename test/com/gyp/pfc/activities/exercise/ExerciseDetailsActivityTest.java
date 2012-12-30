package com.gyp.pfc.activities.exercise;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExerciseDetailsActivity();
	}

	// Private -------------------------------------------------------
	private void intentPassedWithExercise() {
		Exercise exercise = new Exercise();
		exercise.setName(EXERCISE_NAME);
		exercise.setDescription(EXERCISE_DESC);
		Intent intent = new Intent();
		intent.putExtra(ExerciseListActivity.SELECTED_EXERCISE, exercise);
		activity.setIntent(intent);
	}
	// Inner classes -------------------------------------------------
}
