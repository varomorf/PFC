/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.meal.Meal;
import com.gyp.pfc.data.domain.meal.Portion;

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

	@Test
	public void shouldReturnCorrectNutrionalInformationRounded() {
		// GIVEN
		// a meal with two portions
		Portion a = createPortion(90, 100d, 10d, 8d, 5d);
		Portion b = createPortion(50, 100d, 30d, 2d, 2d);
		Meal meal = new Meal();
		meal.addPortion(a);
		meal.addPortion(b);
		// WHEN
		double mealCals = meal.getCalories();
		double mealCarbs = meal.getCarbs();
		double mealProtein = meal.getProtein();
		double mealFats = meal.getFats();
		// THEN
		assertEquals("Should have returned expect calories", 140d, mealCals, 0);
		assertEquals("Should have returned expect carbs", 24d, mealCarbs, 0);
		assertEquals("Should have returned expect protein", 8d, mealProtein, 0);
		assertEquals("Should have returned expect fats", 6d, mealFats, 0);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	private Portion createPortion(int quantity, double calories, double carbs, double protein, double fats) {
		Food f = new Food();
		f.setCalories(calories);
		f.setCarbs(carbs);
		f.setProtein(protein);
		f.setFats(fats);
		Portion p = new Portion();
		p.setFood(f);
		p.setQuantity(quantity);
		return p;
	}

	// Inner classes -------------------------------------------------

}
