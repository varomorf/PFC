/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gyp.pfc.data.domain.food.Food;

/**
 * Tests for the Food entity
 * 
 * @author Alvaro
 * 
 */
public class FoodTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldBeEqualsToAnotherFoodWithTheSameId() {
		// GIVEN
		Food aFood = new Food();
		aFood.setId(1);
		Food anotherFood = new Food();
		anotherFood.setId(1);
		// WHEN
		boolean equals = aFood.equals(anotherFood);
		// THEN
		assertTrue("Foods with the same id should be equal", equals);
	}

	@Test
	public void shouldNotBeEqualToAnotherFoodWithDifferentId() {
		// GIVEN
		Food aFood = new Food();
		aFood.setId(1);
		Food anotherFood = new Food();
		anotherFood.setId(2);
		// WHEN
		boolean equals = aFood.equals(anotherFood);
		// THEN
		assertFalse("Foods with different id shouldn't be equal", equals);
	}

	@Test
	public void shouldNotBeEqualToOtherTypes() {
		// GIVEN
		Food aFood = new Food();
		aFood.setId(1);
		String anotherThing = new String();
		// WHEN
		boolean equals = aFood.equals(anotherThing);
		// THEN
		assertFalse("Foods shouldn't be equal to other types", equals);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
