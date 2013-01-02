package com.gyp.pfc.activities.exercise;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.Exercise;

@RunWith(CustomTestRunner.class)
public class EditExerciseActivityTest extends BaseExerciseTest {

	// Constants -----------------------------------------------------

	private static final String NEW_NAME = "NEW_NAME";
	private static final String NEW_DESC = "NEW_DESC";
	private static final String DUPLICATED_NAME = "DUPLICATED_NAME";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		insertExercise(EXERCISE_NAME, EXERCISE_DESC);
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
		String name = UIUtils.getTextFromUI(activity
				.findViewById(R.id.exerciseName));
		assertThat(name, is(exercise.getName()));
		String description = UIUtils.getTextFromUI(activity
				.findViewById(R.id.exerciseDescription));
		assertThat(description, is(exercise.getDescription()));
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
		// edit button is clicked
		commitButtonIsClicked();
		// THEN
		// assert toast is shown with error
		assertToastText(R.string.exerciseEdited);
		// exercise data is changed
		assertThat(exercise.getName(), is(NEW_NAME));
		assertThat(exercise.getDescription(), is(NEW_DESC));
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
		insertExercise(DUPLICATED_NAME, EXERCISE_DESC);
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
