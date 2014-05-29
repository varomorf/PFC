/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * Tests for {@link Meal} entities
 * 
 * @author Alvaro
 * 
 */
public class MealTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldReturnExpectedString() {
		// GIVEN
		Meal meal = new Meal();
		meal.setId(1);
		Date date = new Date();
		date = DateUtils.setDays(date, 1);
		date = DateUtils.setMonths(date, 0);
		date = DateUtils.setYears(date, 2014);
		meal.setDate(date);
		String expected = "1 - Null meal@01/01/2014 with 0 portions";
		// WHEN
		String ret = meal.toString();
		// THEN
		assertEquals("Should have returned expected string", expected, ret);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
