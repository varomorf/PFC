package com.gyp.pfc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gyp.pfc.data.domain.exercise.Exercise;

/**
 * Unitary testing for Exercise entity
 * 
 * @author Alvaro
 * 
 */
public class ExerciseTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	
	@Test
	public void shouldBeEqualById() {
		// GIVEN
		Exercise exercise1 = new Exercise();
		exercise1.setId(1);
		Exercise exercise2 = new Exercise();
		exercise2.setId(1);
		// WHEN
		boolean equal = exercise1.equals(exercise2);
		// THEN
		assertTrue("Exercises should have been equal", equal);
	}
	
	@Test
	public void shouldBeNotEqualsById() {
		// GIVEN
		Exercise exercise1 = new Exercise();
		exercise1.setId(1);
		Exercise exercise2 = new Exercise();
		exercise2.setId(2);
		// WHEN
		boolean equal = exercise1.equals(exercise2);
		// THEN
		assertFalse("Exercises should have not been equal", equal);
	}

	@Test
	public void shouldBeNotEqualsByType() {
		// GIVEN
		Exercise exercise1 = new Exercise();
		exercise1.setId(1);
		// WHEN
		boolean equal = exercise1.equals("Not an exercise");
		// THEN
		assertFalse("Exercises should have not been equal", equal);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
