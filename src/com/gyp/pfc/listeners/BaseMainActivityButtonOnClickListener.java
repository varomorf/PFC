package com.gyp.pfc.listeners;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Base {@link OnClickListener} for the buttons shown in the main activity for
 * each main entity.
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseMainActivityButtonOnClickListener implements
		OnClickListener {

	// Constants -----------------------------------------------------
	/**
	 * Tag for logging
	 */
	public static final String TAG = "BaseMainActivityButtonOnClickListener";

	// Attributes ----------------------------------------------------

	/**
	 * The qualified name of the activity to be launched
	 */
	private String entryName;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Constructor
	 * 
	 * @param entryName
	 *            The value to be used for the activity
	 */
	public BaseMainActivityButtonOnClickListener(String entryName) {
		this.entryName = entryName;
	}

	// Public --------------------------------------------------------
	/**
	 * Called when a view has been clicked.
	 * 
	 * @param v
	 *            The view that was clicked.
	 */
	@SuppressWarnings("rawtypes")
	public void onClick(View view) {
		// get activity name from entry name
		String activityName = prepareActivityQualifiedName(entryName);
		// get context from the passed view
		Context context = view.getContext();

		Class destinyClass;
		try {
			// try to get the class
			destinyClass = Class.forName(activityName);
			// create intent from source activity to retrieved class
			Intent intent = new Intent(context, destinyClass);
			// start the activity
			context.startActivity(intent);
		} catch (ClassNotFoundException e) {
			// XXX how will the error be handled on production?
			Log.e(TAG, "Class not found while executing onClick for "
					+ activityName, e);
		}
	}

	/**
	 * <p>
	 * Must return the qualified name of the activity to be called for the
	 * passed entryName
	 * </p>
	 * 
	 * @param entryName
	 *            the name of the section for which to prepare the listener
	 * @return the qualified name of the activity
	 */
	public String prepareActivityQualifiedName(String entryName) {
		assert StringUtils.isNotBlank(entryName);
		// remove the last character (always s)
		String entityName = StringUtils.chop(entryName);
		// package name is entity name in small caps
		String packageName = StringUtils.uncapitalize(entityName);

		// get qualified name calling to the abstract method
		String qualifiedName = prepareActivityQualifiedName(packageName,
				entityName);

		return qualifiedName;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	/**
	 * <p>
	 * Must return the qualified name of the activity to be called for the
	 * passed entity and package
	 * </p>
	 * 
	 * @param packageName
	 *            the package in which the activity is
	 * @param entityName
	 *            the name of the entity for which to prepare the activity
	 *            qualified name
	 * @return the qualified name of the activity
	 */
	protected abstract String prepareActivityQualifiedName(String packageName,
			String entityName);
	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}