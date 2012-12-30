package com.gyp.pfc;

import android.view.View;
import android.widget.TextView;

/**
 * Utility methods to use for handling UI components
 * 
 * @author Alvaro
 * 
 */
public class UIUtils {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------
	/**
	 * <p>
	 * Returns the String representation of the text held by the passed view as
	 * long as this view is a {@link TextView}
	 * </p>
	 * 
	 * @param view
	 *            The view from which to extract the text
	 * @return The extracted text of null if the view is null or not
	 *         {@link TextView}
	 */
	public static String getTextFromUI(View view) {
		// null will be returned if the view is not valid
		String text = null;
		if (validTextView(view)) {
			// get the text and convert to string
			text = ((TextView) view).getText().toString();
		}
		return text;
	}

	/**
	 * Clears the text of the passed view as long as it's a {@link TextView}
	 * 
	 * @param view
	 *            The view to clear its text
	 */
	public static void clearView(View view) {
		if (validTextView(view)) {
			// clear the view setting empty string
			((TextView) view).setText("");
		}
	}

	// Constructors --------------------------------------------------

	private UIUtils() {
		// NOOP
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	private static boolean validTextView(View view) {
		// view is valid if not null and is a TextView
		return view != null && view instanceof TextView;
	}

	// Inner classes -------------------------------------------------
}
