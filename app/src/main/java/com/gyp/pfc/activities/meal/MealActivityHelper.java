/**
 * 
 */
package com.gyp.pfc.activities.meal;

import java.util.Date;

import android.app.Activity;

import com.gyp.pfc.activities.BaseActivityHelper;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;

/**
 * Helper activity class for meal related activities
 * 
 * @author Alvaro
 * 
 */
public class MealActivityHelper extends BaseActivityHelper {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link MealActivityHelper} specifying its activity
	 * 
	 * @param activity
	 *            the activity for the new {@link MealActivityHelper}
	 */
	public MealActivityHelper(Activity activity) {
		super(activity);
	}

	// Public --------------------------------------------------------

	/**
	 * Creates and returns a new {@link Meal} with the passed date and
	 * {@link MealName}
	 * 
	 * @param date
	 *            the date of the new meal
	 * @param mealName
	 *            the name of the new meal
	 * @return the new meal created
	 */
	public Meal createMealFor(Date date, MealName mealName) {
		Meal meal = new Meal();
		meal.setDate(date);
		meal.setName(mealName);
		return meal;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
