/**
 * 
 */
package com.gyp.pfc.activities.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

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
	 * Shows a dialog for confirming the shown food deletion, deleting it only
	 * if affirmative response is given by the user
	 * 
	 * @param yesOptionListener
	 *            OnClickListener for affirmative action
	 */
	public void deleteWithDialog(OnClickListener yesOptionListener) {
		new AlertDialog.Builder(activity).setMessage(R.string.assureFoodDeletion).setCancelable(false)
				.setPositiveButton(android.R.string.yes, yesOptionListener)
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do not delete -> close dialog
						dialog.cancel();
					}
				}).create().show();
	}

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
