package com.gyp.pfc.listeners;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * {@link OnClickListener} for the buttons shown in the main activity for
 * directly adding a new main entity entry.
 * 
 * @author Alvaro
 * 
 */
public class AddButtonOnClickListener implements OnClickListener {

	/**
	 * Tag for logging
	 */
	public static final String TAG = "AddButtonOnClickListener";

	/**
	 * Prefix for destiny activity's class name
	 */
	public static final String PRE = "com.gyp.pfc.activities.Add";
	/**
	 * Suffix for destiny activity's class name
	 */
	public static final String POST = "Activity";

	/**
	 * The name of the entry for which the listener is being created
	 */
	private String entryName;

	/**
	 * Sets the {@link #entryName}
	 * 
	 * @param entryName
	 *            The value to be set to the {@link #entryName}. Must not be
	 *            blank.
	 */
	public AddButtonOnClickListener(String entryName) {
		assert StringUtils.isNotBlank(entryName);
		// remove last character from the entryName that will always be an 's'
		this.entryName = StringUtils.chop(entryName);
	}

	@Override
	public void onClick(View view) {
		// get context from the passed view
		Context context = view.getContext();
		// prepare qualified class name
		String className = PRE + entryName + POST;

		Class destinyClass;
		try {
			// try to get the class
			destinyClass = Class.forName(className);
			// create intent from source activity to gotten class
			Intent intent = new Intent(context, destinyClass);
			// start the activity
			context.startActivity(intent);
		} catch (ClassNotFoundException e) {
			// XXX how will the error be handled on production?
			Log.e(TAG, "Class not found while executing onClick for "
					+ className, e);
		}
	}
}