package com.gyp.pfc;

import android.view.View;
import android.widget.TextView;

/**
 * Utility methods to use for handling UI components
 * 
 * @author Alvaro
 * 
 */
public final class UIUtils {

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
		if (view instanceof TextView) {
			// get the text and convert to string
			text = ((TextView) view).getText().toString();
		}
		return text;
	}

	/**
	 * <p>
	 * Sets the passed string as the passed view's text as long as this view is
	 * a {@link TextView}
	 * </p>
	 * 
	 * @param view
	 *            The view to which to set the text
	 * @param text
	 *            The text to be set
	 */
	public static void setTextToUI(View view, CharSequence text) {
		if (view instanceof TextView) {
			// get the text and convert to string
			((TextView) view).setText(text);
		}
	}

	/**
	 * Clears the text of the passed view as long as it's a {@link TextView}
	 * 
	 * @param view
	 *            The view to clear its text
	 */
	public static void clearView(View view) {
		if (view instanceof TextView) {
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

	// Inner classes -------------------------------------------------
}
