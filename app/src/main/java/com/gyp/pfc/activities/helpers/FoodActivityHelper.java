/**
 * 
 */
package com.gyp.pfc.activities.helpers;

import android.app.Activity;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.food.Food;

/**
 * Helper activity class for food related activities
 * 
 * @author Alvaro
 * 
 */
public class FoodActivityHelper extends BaseActivityHelper {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Creates a new {@link FoodActivityHelper} specifying its activity
	 * 
	 * @param activity
	 *            the activity for the new {@link FoodActivityHelper}
	 */
	public FoodActivityHelper(Activity activity) {
		super(activity);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	/**
	 * Fills the passed food with the data from the food_add_food layout
	 * 
	 * @param food
	 *            the food to be filled with data
	 */
	public void fillFoodData(Food food) {
		// get required food data
		food.setName(getEditTextViewAsserting(R.id.foodNameText, R.string.foodNameError));
		food.setCalories(getEditTextViewAsserting(R.id.caloriesText, R.string.caloriesError));
		food.setProtein(getEditTextViewAsserting(R.id.proteinsText, R.string.proteinsError));
		food.setCarbs(getEditTextViewAsserting(R.id.carbsText, R.string.carbsError));
		food.setFats(getEditTextViewAsserting(R.id.fatsText, R.string.fatsError));
		// get the rest of the data
		food.setBrandName(getTextFromUI(R.id.foodBrandText));
		food.setSugar(getTextFromUI(R.id.sugarText));
		food.setFiber(getTextFromUI(R.id.fiberText));
		food.setSaturatedFats(getTextFromUI(R.id.saturatedFatsText));
		food.setSodium(getTextFromUI(R.id.sodiumText));
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
