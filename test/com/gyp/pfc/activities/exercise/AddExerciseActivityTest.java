package com.gyp.pfc.activities.exercise;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;

@RunWith(CustomTestRunner.class)
public class AddExerciseActivityTest extends BaseExerciseTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		List<Exercise> exercises = dao.queryForAll();
		dao.delete(exercises);
	}

	@Test
	public void shouldSaveExercise() {
		// GIVEN
		createActivity();
		// WHEN
		enterName(EXERCISE_NAME);
		enterDescription(EXERCISE_DESC);
		commitButtonIsClicked();
		// THEN
		List<Exercise> exercises = dao.queryForAll();
		assertThat(exercises.size(), is(1));
		assertThat(exercises.get(0).getName(), is(EXERCISE_NAME));
		assertThat(exercises.get(0).getDescription(), is(EXERCISE_DESC));
		assertToastText(R.string.exerciseCreated);
		assertViewText(R.id.exerciseName, "");
		assertViewText(R.id.exerciseDescription, "");
	}

	@Test
	public void shouldNotAllowEmptyName() {
		// GIVEN
		createActivity();
		// WHEN
		assertViewText(R.id.exerciseName, "");
		commitButtonIsClicked();
		// THEN
		assertToastText(R.string.exerciseNameBlank);
	}

	@Test
	public void shouldNotAllowDuplicatedName() {
		// GIVEN
		createActivity();
		insertExercise(EXERCISE_NAME, null);
		// WHEN
		enterName(EXERCISE_NAME);
		commitButtonIsClicked();
		// THEN
		assertToastText(R.string.exerciseNameDuplicated);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new AddExerciseActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
