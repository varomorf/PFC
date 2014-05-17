/**
 * 
 */
package com.gyp.pfc.activities.food;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivity;

/**
 * Helper activity class for food related activities
 * 
 * @author Alvaro
 * 
 */
public class FoodActivityHelper extends BaseActivity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	
	/**
	 * The context to be used
	 */
	private Context context;

	// Static --------------------------------------------------------

	/** Singleton instance of the FoodActivityHelper */
	private static FoodActivityHelper instance;

	/**
	 * Returns the singleton instance of the FoodManager
	 * 
	 * @return the singleton instance of the FoodManager
	 */
	public static FoodActivityHelper callFor(Context context) {
		if (null == instance) {
			instance = new FoodActivityHelper();
		}
		instance.context = context;
		return instance;
	}

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

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
		new AlertDialog.Builder(context).setMessage(R.string.assureFoodDeletion).setCancelable(false)
				.setPositiveButton(android.R.string.yes, yesOptionListener)
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do not delete -> close dialog
						dialog.cancel();
					}
				}).create().show();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
