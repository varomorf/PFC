package com.gyp.pfc.listeners;

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
	private static final String PRE = "com.gyp.pfc.activities.";
	private static final String POST = "ListActivity";

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
		super(entryName);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected String prepareActivityQualifiedName(String packageName,
			String entityName) {
		// prepare qualified name putting the pieces together
		StringBuffer buffer = new StringBuffer(PRE);
		buffer.append(packageName);
		buffer.append('.');
		buffer.append(entityName);
		buffer.append(POST);

		return buffer.toString();
	}
	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}