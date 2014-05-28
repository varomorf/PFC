package com.gyp.pfc.activities;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.exception.MandatoryFieldNotFilledException;

/**
 * Base Activity for the application. Provides useful methods for GUI control.
 * 
 * @author Alvaro
 * 
 */
public class BaseActivityHelper {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * The context to be used
	 */
	protected Activity activity;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link BaseActivityHelper} specifying its activity
	 * 
	 * @param activity
	 *            the activity for the new {@link BaseActivityHelper}
	 */
	public BaseActivityHelper(Activity activity) {
		super();
		this.activity = activity;
	}

	// Public --------------------------------------------------------

	/**
	 * Returns the value of the EditText with the passed id. If the value is
	 * blank, an error will be shown for the specified string id and an
	 * IllegalArgumentException will be thrown
	 * 
	 * @param viewId
	 *            the id of the EditText
	 * @param errorId
	 *            the id of the string for the error
	 * @return the value of the EditText
	 * @throws MandatoryFieldNotFilledException
	 *             if the name is not filled
	 */
	public String getEditTextViewAsserting(int viewId, int errorId) {
		String value = getTextFromUI(viewId);
		if (StringUtils.isBlank(value)) {
			throw new MandatoryFieldNotFilledException(viewId, activity.getString(errorId));
		}
		return value;
	}

	/**
	 * Returns the String representation of the text held by the view with the
	 * specified id as long as this view is a {@link TextView}
	 * 
	 * @param viewId
	 *            The id of the view from which to extract the text
	 * @return The extracted text of null if the viewId does not belongs to a
	 *         {@link TextView}
	 */
	public String getTextFromUI(int viewId) {
		return getTextFromUI(activity.findViewById(viewId));
	}

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
	public String getTextFromUI(View view) {
		return UIUtils.getTextFromUI(view);
	}

	/**
	 * Sets the text with the passed id as the view's text as long as the
	 * specified viewId belongs to a {@link TextView}
	 * 
	 * @param viewId
	 *            The id of the view to which to set the text
	 * @param textId
	 *            The id of the text to be set
	 */
	public void setTextToUI(int viewId, int textId) {
		setTextToUI(viewId, activity.getText(textId));
	}

	/**
	 * Sets the passed text as the view's text as long as the specified viewId
	 * belongs to a {@link TextView}
	 * 
	 * @param viewId
	 *            The id of the view to which to set the text
	 * @param textId
	 *            The text to be set
	 */
	public void setTextToUI(int viewId, CharSequence text) {
		UIUtils.setTextToUI(activity.findViewById(viewId), text);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
