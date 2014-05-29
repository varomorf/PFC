/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the {@link MealName} entity
 * 
 * @author Alvaro
 * 
 */
public class MealNameTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldCompareCorrectly() {
		// GIVEN
		MealName greater = new MealName();
		greater.setOrder(1);
		MealName lower = new MealName();
		lower.setOrder(0);
		// WHEN
		int lessThan = lower.compareTo(greater);
		int equal = lower.compareTo(lower);
		int greaterThan = greater.compareTo(lower);
		// THEN
		assertEquals("Lower should have been less than greater", -1, lessThan);
		assertEquals("Lower should have been equal to lower", 0, equal);
		assertEquals("Greater should have been greater than lower", 1, greaterThan);
	}

	@Test
	public void shouldBeEqualsToAnotherFoodWithTheSameId() {
		// GIVEN
		MealName oneMealName = new MealName();
		oneMealName.setId(1);
		MealName anotherMealName = new MealName();
		anotherMealName.setId(1);
		// WHEN
		boolean equals = oneMealName.equals(anotherMealName);
		// THEN
		assertTrue("MealName objects should be equal", equals);
	}

	@Test
	public void shouldNotBeEqualToAnotherFoodWithDifferentId() {
		// GIVEN
		MealName oneMealName = new MealName();
		oneMealName.setId(1);
		MealName anotherMealName = new MealName();
		anotherMealName.setId(0);
		// WHEN
		boolean equals = oneMealName.equals(anotherMealName);
		// THEN
		assertFalse("MealName objects shouldn't be equal", equals);
	}

	@Test
	public void shouldNotBeEqualToOtherTypes() {
		// GIVEN
		MealName oneMealName = new MealName();
		String anotherThing = new String();
		// WHEN
		boolean equals = oneMealName.equals(anotherThing);
		// THEN
		assertFalse("MealName objects shouldn't be equal to other types", equals);
	}

	@Test
	public void shouldReturnExpectedString() {
		// GIVEN
		MealName theMealName = new MealName();
		theMealName.setId(1);
		theMealName.setName("Desayuno");
		theMealName.setOrder(1);
		String expected = "1 - Desayuno@1";
		// WHEN
		String ret = theMealName.toString();
		// THEN
		assertEquals(expected, ret);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
