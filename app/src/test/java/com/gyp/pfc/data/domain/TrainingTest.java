/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;

/**
 * Tests for the {@link Training} entity
 * 
 * @author Alvaro
 * 
 */
public class TrainingTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldComputeCaloriesFromExerciseCaloriesAndDuration() {
		// GIVEN
		// two exercises, one with 100 cals and the other 200
		Exercise one = new Exercise();
		one.setBurntCalories(100);
		Exercise two = new Exercise();
		one.setBurntCalories(200);
		// one training exercise for 1/2 hour of one rep of exercise one
		TrainingExercise te1 = new TrainingExercise();
		te1.setExercise(one);
		te1.setReps(1);
		te1.setSeconds(1800);
		// one trainingexercise for 15 reps of 1 minute of exercise two
		TrainingExercise te2 = new TrainingExercise();
		te2.setExercise(two);
		te2.setReps(15);
		te2.setSeconds(60);
		// a training with both trainingexercise
		Training training = new Training();
		training.getExercises().add(te1);
		training.getExercises().add(te2);
		// WHEN
		int calories = training.getBurntCalories();
		// THEN
		assertEquals("Training doesn't return expected burnt calories", 100, calories);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
