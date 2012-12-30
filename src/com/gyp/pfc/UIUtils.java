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
		if (view != null && view instanceof TextView) {
			text = ((TextView) view).getText().toString();
		}
		return text;
	}

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
