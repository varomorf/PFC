/**
 * 
 */
package com.gyp.pfc.data.domain.biometric;

import com.gyp.pfc.R;

/**
 * Sex the user can have
 * 
 * @author Alvaro
 * 
 */
public enum UserSex {

	// Constants -----------------------------------------------------

	MAN(R.string.man), WOMAN(R.string.woman);

	// Attributes ----------------------------------------------------

	/** String id for the message that represents this sex */
	private int stringId;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link UserSex} specifying the string representation's id
	 * 
	 * @param stringId
	 *            the string representation's id
	 */
	private UserSex(int stringId) {
		this.stringId = stringId;
	}

	// Public --------------------------------------------------------

	public int getStringId() {
		return stringId;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
