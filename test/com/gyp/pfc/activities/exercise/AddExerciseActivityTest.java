package com.gyp.pfc.activities.exercise;

import org.junit.Before;
import org.junit.Test;

import android.app.Activity;

public class AddExerciseActivityTest extends BaseExerciseTest {

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
	public void shouldSaveExercise() {
		// GIVEN
		// activity created
		// WHEN
		// info is entered
		// add button is clicked
		// THEN
		// exercise is added to DB
	}
	
	@Test
	public void shouldNotAllowEmptyName(){
		// GIVEN
		// activity created
		// WHEN
		// assert name is blank
		// add button is clicked
		// THEN
		// toast with error is shown
	}
	
	@Test
	public void shouldNotAllowDuplicatedName(){
		// GIVEN
		// activity created
		// exercise with name x in DB
		// WHEN
		// name x is entered
		// add button is clicked
		// THEN
		// toast with error is shown
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity createActivity() {
		return new AddExerciseActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
