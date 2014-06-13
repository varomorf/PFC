package com.gyp.pfc;

import android.view.View;
import android.view.ViewGroup;
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
	 * <p>
	 * Overloaded setTextToUI(View view, CharSequence text) that receives an int
	 * </p>
	 * 
	 * @param view
	 *            The view to which to set the text
	 * @param value
	 *            The int value to be converted to text
	 */
	public static void setTextToUI(View view, int value) {
		setTextToUI(view, Integer.toString(value));
	}

	/**
	 * <p>
	 * Gets the child at pos from the passed view as long as the view is a
	 * ViewGroup
	 * </p>
	 * 
	 * @param view
	 *            The view from which to get the child
	 * @param pos
	 *            The position from which to get the child
	 * @return The child gotten
	 */
	public static View getChildFromView(View view, int pos) {
		View child = null;
		if (view instanceof ViewGroup) {
			child = ((ViewGroup) view).getChildAt(pos);
		}
		return child;
	}

	/**
	 * Parses the passed String as a double and returns its parsed value as long
	 * as it's not null nor unparseable.
	 * 
	 * @param value
	 *            String to be parsed
	 * @return Double value of the String or <code>null</code> if unparseable
	 */
	public static Double parseDouble(String value) {
		Double output = null;
		if (null != value) {
			try {
				output = Double.valueOf(value);
			} catch (NumberFormatException e) {
				// output will stay being null
			}
		}
		return output;
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
