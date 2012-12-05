package com.gyp.pfc.listeners;

import org.apache.commons.lang.StringUtils;

/**
 * Listener for the buttons shown in the main activity for listing each main
 * entity.
 * 
 * @author Alvaro
 * 
 */
public class ListButtonOnClickListener extends
		BaseMainActivityButtonOnClickListener {

	// Constants -----------------------------------------------------
	/**
	 * Prefix for destiny activity's class name
	 */
	public static final String PRE = "com.gyp.pfc.activities.";
	/**
	 * Suffix for destiny activity's class name
	 */
	public static final String POST = "ListActivity";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Sets the activityName from the passed entryName
	 * 
	 * @param entryName
	 *            The value to be used for setting the activityName
	 */
	public ListButtonOnClickListener(String entryName) {
		assert StringUtils.isNotBlank(entryName);
		setActivityName(PRE + entryName + POST);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}