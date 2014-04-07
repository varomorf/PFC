package com.gyp.pfc.activities;

import android.view.View;
import android.widget.TextView;

import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Base Activity for the application. Provides useful methods for GUI control.
 * 
 * @author Alvaro
 * 
 */
public class BaseActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

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
		return getTextFromUI(findViewById(viewId));
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
	public static String getTextFromUI(View view) {
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
		setTextToUI(viewId, getText(textId));
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
		UIUtils.setTextToUI(findViewById(viewId), text);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
