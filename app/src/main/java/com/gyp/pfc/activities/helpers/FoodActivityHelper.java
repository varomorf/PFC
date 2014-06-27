/**
 * 
 */
package com.gyp.pfc.activities.helpers;

import android.app.Activity;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.builder.FoodBuilder;
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

	/**
	 * Fills the passed food with the data from the food_add_food layout
	 * 
	 * @param fb
	 *            the FoodBuilder to use for filling with data
	 */
	public void fillFoodData(FoodBuilder fb) {
		// get required food data
		fb.name(getEditTextViewAsserting(R.id.foodNameText, R.string.foodNameError))
				.calories(getEditTextViewAsserting(R.id.caloriesText, R.string.caloriesError))
				.protein(getEditTextViewAsserting(R.id.proteinsText, R.string.proteinsError))
				.carbs(getEditTextViewAsserting(R.id.carbsText, R.string.carbsError))
				.fats(getEditTextViewAsserting(R.id.fatsText, R.string.fatsError));
		// get the rest of the data
		fb.brandName(getTextFromUI(R.id.foodBrandText)).sugar(getTextFromUI(R.id.sugarText))
				.fiber(getTextFromUI(R.id.fiberText)).saturatedFats(getTextFromUI(R.id.saturatedFatsText))
				.sodium(getTextFromUI(R.id.sodiumText));
	}

	/**
	 * Fills the passed food with the data from the food_add_food layout
	 * 
	 * @param food
	 *            the food to use for filling with data
	 */
	public void fillFoodData(Food food) {
		fillFoodData(new FoodBuilder(food));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
