package com.gyp.pfc.listeners;

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
	private static final String PRE = "com.gyp.pfc.activities.";

	private static final String MIDDLE = ".Add";

	private static final String POST = "Activity";

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
		super(entryName);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	protected String prepareActivityQualifiedName(String packageName,
			String entityName) {
		// prepare qualified name putting the pieces together
		StringBuffer buffer = new StringBuffer(PRE);
		buffer.append(packageName);
		buffer.append(MIDDLE);
		buffer.append(entityName);
		buffer.append(POST);

		return buffer.toString();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}