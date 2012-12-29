package com.gyp.pfc.activities.exercise;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;

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
		// intent passed with Exercise
		// WHEN
		// activity is shown
		// THEN
		// activity shows correct values
	}

	@Test
	public void shouldHaveMenuForCrudOptions() {
		// GIVEN
		// intent passed with Exercise
		// WHEN
		// activity is shown
		// menu key is pressed
		// THEN
		// CRUD menu must be shown
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity createActivity() {
		return new ExerciseDetailsActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
