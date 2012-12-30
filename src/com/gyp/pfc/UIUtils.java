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
	public static String getTextFromUI(View view) {
		String text = null;
		if (validTextView(view)) {
			text = ((TextView) view).getText().toString();
		}
		return text;
	}

	public static void clearView(View view) {
		if (validTextView(view)) {
			((TextView) view).setText("");
		}
	}

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	private static boolean validTextView(View view) {
		return view != null && view instanceof TextView;
	}

	// Inner classes -------------------------------------------------
}
