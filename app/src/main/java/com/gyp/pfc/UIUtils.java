package com.gyp.pfc;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
	 * <p>
	 * Gets the sibling which holds the passed position on the passed view's
	 * parent
	 * </p>
	 * 
	 * @param view
	 *            The view from which to get the sibling
	 * @param pos
	 *            The position of the sibling in the view's parent
	 * @return The sibling view
	 */
	public static View getSibling(View view, int pos) {
		View sibling = null;
		ViewParent parent = view.getParent();
		if (parent instanceof ViewGroup) {
			sibling = getChildFromView((View) parent, pos);
		}
		return sibling;
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
