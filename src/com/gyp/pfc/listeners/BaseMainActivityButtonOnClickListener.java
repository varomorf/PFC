package com.gyp.pfc.listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Base {@link OnClickListener} for the buttons shown in the main activity for
 * each main entity.
 * 
 * @author Alvaro
 * 
 */
public class BaseMainActivityButtonOnClickListener implements OnClickListener {

	// Constants -----------------------------------------------------
	/**
	 * Tag for logging
	 */
	public static final String TAG = "BaseMainActivityButtonOnClickListener";

	// Attributes ----------------------------------------------------

	/**
	 * The qualified name of the activity to be launched
	 */
	protected String activityName;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onClick(View view) {
		// get context from the passed view
		Context context = view.getContext();

		Class destinyClass;
		try {
			// try to get the class
			destinyClass = Class.forName(activityName);
			// create intent from source activity to retrieved class
			Intent intent = new Intent(context, destinyClass);
			// start the activity
			context.startActivity(intent);
		} catch (ClassNotFoundException e) {
			// XXX how will the error be handled on production?
			Log.e(TAG, "Class not found while executing onClick for "
					+ activityName, e);
		}
	}
	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}