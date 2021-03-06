package com.gyp.pfc.activities.helpers;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

	/** Flag for workaround known DatePickerDialog bug */
	private boolean onDateSetCalled;

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
	 * Returns the value of the EditText with the passed id. If the value is blank, an error will be shown for the
	 * specified string id and an IllegalArgumentException will be thrown
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
	 * Returns the String representation of the text held by the view with the specified id as long as this view is
	 * a {@link TextView}
	 * 
	 * @param viewId
	 *            The id of the view from which to extract the text
	 * @return The extracted text of null if the viewId does not belongs to a {@link TextView}
	 */
	public String getTextFromUI(int viewId) {
		return getTextFromUI(activity.findViewById(viewId));
	}

	/**
	 * <p>
	 * Returns the String representation of the text held by the passed view as long as this view is a
	 * {@link TextView}
	 * </p>
	 * 
	 * @param view
	 *            The view from which to extract the text
	 * @return The extracted text of null if the view is null or not {@link TextView}
	 */
	public String getTextFromUI(View view) {
		return UIUtils.getTextFromUI(view);
	}

	/**
	 * Sets the text with the passed id as the view's text as long as the specified viewId belongs to a
	 * {@link TextView}
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
	 * Sets the passed text as the view's text as long as the specified viewId belongs to a {@link TextView}
	 * 
	 * @param viewId
	 *            The id of the view to which to set the text
	 * @param textId
	 *            The text to be set
	 */
	public void setTextToUI(int viewId, CharSequence text) {
		UIUtils.setTextToUI(activity.findViewById(viewId), text);
	}

	/**
	 * Returns an int value from a TextView
	 * 
	 * @param view
	 *            the view to get the int from
	 * @return the int extracted from the view
	 */
	public int getIntFromUI(View view) {
		String text = getTextFromUI(view);
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Returns an int value from a TextView
	 * 
	 * @param viewId
	 *            The id of the view from which to extract the text
	 * @return the int extracted from the view
	 */
	public int getIntFromUI(int viewId) {
		return getIntFromUI(activity.findViewById(viewId));
	}

	/**
	 * Shows a dialog for confirming the deletion of an entity, deleting it only if affirmative response is given
	 * by the user
	 * 
	 * @param messageId
	 *            the id of the string with the displayed message
	 * @param yesOptionListener
	 *            OnClickListener for affirmative action
	 */
	public void deleteWithDialog(int messageId, OnClickListener yesOptionListener) {
		new AlertDialog.Builder(activity).setMessage(messageId).setCancelable(false)
				.setPositiveButton(android.R.string.yes, yesOptionListener)
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// do not delete -> close dialog
						dialog.cancel();
					}
				}).create().show();
	}

	/**
	 * Shows a {@link DatePickerDialog} for today's date
	 */
	public void showDatePickerDialogForToday() {
		assert activity instanceof OnDateSetListener;
		onDateSetCalled = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		new DatePickerDialog(activity, (OnDateSetListener) activity, year, month, day).show();
	}

	/**
	 * Returns the date from the data of a DatePickerDialog
	 * 
	 * @param year
	 *            the year from the DatePickerDialog
	 * @param monthOfYear
	 *            the monthOfYear from the DatePickerDialog
	 * @param dayOfMonth
	 *            the dayOfMonth from the DatePickerDialog
	 * @return the converted date
	 */
	public Date getDateFromPicker(int year, int monthOfYear, int dayOfMonth) {
		if (!onDateSetCalled) {
			onDateSetCalled = true;
		} else {
			onDateSetCalled = false;
			return null;
		}
		Date date = new Date();
		date = DateUtils.setYears(date, year);
		date = DateUtils.setMonths(date, monthOfYear);
		date = DateUtils.setDays(date, dayOfMonth);
		return date;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
