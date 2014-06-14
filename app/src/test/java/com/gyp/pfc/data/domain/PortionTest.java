/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the {@link Portion} entity
 * 
 * @author Alvaro
 * 
 */
public class PortionTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldReturnExpectedString() {
		// GIVEN
		Portion portion = new Portion();
		portion.setId(1);
		portion.setQuantity(100);
		String expected = "1 - 100gr. of Null Food for meal -1";
		// WHEN
		String ret = portion.toString();
		// THEN
		assertEquals("Should have returned expected string", expected, ret);
	}

	@Test
	public void shouldReturnExpectedNutritionalInformation() {
		// GIVEN
		// portions should round amounts
		Double foodCalsPer100 = 300d;
		Double foodCarbsPer100 = 20d;
		Double foodProteinPer100 = 9d;
		Double foodFatsPer100 = 30d;
		Food food = new Food();
		food.setCalories(foodCalsPer100);
		food.setCarbs(foodCarbsPer100);
		food.setProtein(foodProteinPer100);
		food.setFats(foodFatsPer100);
		Portion portion = new Portion();
		portion.setId(1);
		portion.setQuantity(35);
		portion.setFood(food);
		// WHEN
		double portionCals = portion.getCalories();
		double portionCarbs = portion.getCarbs();
		double portionProtein = portion.getProtein();
		double portionFats = portion.getFats();
		// THEN
		assertEquals("Should have returned expect calories", 105d, portionCals, 0);
		assertEquals("Should have returned expect carbs", 7d, portionCarbs, 0);
		assertEquals("Should have returned expect protein", 3.15d, portionProtein, 0);
		assertEquals("Should have returned expect fats", 10.5d, portionFats, 0);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
