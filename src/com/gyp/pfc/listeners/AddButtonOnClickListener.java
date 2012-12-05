package com.gyp.pfc.listeners;

import org.apache.commons.lang.StringUtils;

/**
 * Listener for the buttons shown in the main activity for directly adding a new
 * main entity entry.
 * 
 * @author Alvaro
 * 
 */
public class AddButtonOnClickListener extends
		BaseMainActivityButtonOnClickListener {

	// Constants -----------------------------------------------------
	/**
	 * Prefix for destiny activity's class name
	 */
	public static final String PRE = "com.gyp.pfc.activities.Add";
	/**
	 * Suffix for destiny activity's class name
	 */
	public static final String POST = "Activity";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Sets the activityName from the passed entryName.
	 * 
	 * @param entryName
	 *            The value to be used for setting the activityName
	 */
	public AddButtonOnClickListener(String entryName) {
		assert StringUtils.isNotBlank(entryName);
		// remove last character from the entryName that will always be an 's'
		setActivityName(PRE + StringUtils.chop(entryName) + POST);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}