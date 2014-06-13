/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link TrainingExercise} entity
 * 
 * @author Alvaro
 * 
 */
public class TrainingExerciseTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldBeEqualsToAnotherTrainingExerciseWithTheSameId() {
		// GIVEN
		TrainingExercise aTrainingExercise = new TrainingExercise();
		aTrainingExercise.setId(1);
		TrainingExercise anotherTrainingExercise = new TrainingExercise();
		anotherTrainingExercise.setId(1);
		// WHEN
		boolean equals = aTrainingExercise.equals(anotherTrainingExercise);
		// THEN
		assertTrue("TrainingExercises with the same id should be equal", equals);
	}

	@Test
	public void shouldNotBeEqualToAnotherTrainingExerciseWithDifferentId() {
		// GIVEN
		TrainingExercise aTrainingExercise = new TrainingExercise();
		aTrainingExercise.setId(1);
		TrainingExercise anotherTrainingExercise = new TrainingExercise();
		anotherTrainingExercise.setId(2);
		// WHEN
		boolean equals = aTrainingExercise.equals(anotherTrainingExercise);
		// THEN
		assertFalse("TrainingExercises with different id shouldn't be equal", equals);
	}

	@Test
	public void shouldNotBeEqualToOtherTypes() {
		// GIVEN
		TrainingExercise aTrainingExercise = new TrainingExercise();
		aTrainingExercise.setId(1);
		String anotherThing = new String();
		// WHEN
		boolean equals = aTrainingExercise.equals(anotherThing);
		// THEN
		assertFalse("TrainingExercises shouldn't be equal to other types", equals);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
