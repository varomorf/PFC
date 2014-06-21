/**
 * 
 */
package com.gyp.pfc.activities.helpers;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.meal.Meal;
import com.gyp.pfc.data.domain.meal.MealName;

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
	
	/**
	 * Shows a dialog for confirming a portion deletion, deleting it only
	 * if affirmative response is given by the user
	 * 
	 * @param yesOptionListener
	 *            OnClickListener for affirmative action
	 */
	public void deleteWithDialog(OnClickListener yesOptionListener) {
		new AlertDialog.Builder(activity).setMessage(R.string.assurePortionDeletion).setCancelable(false)
				.setPositiveButton(android.R.string.yes, yesOptionListener)
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do not delete -> close dialog
						dialog.cancel();
					}
				}).create().show();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
