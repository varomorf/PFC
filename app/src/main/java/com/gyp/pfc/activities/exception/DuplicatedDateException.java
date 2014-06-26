/**
 * 
 */
package com.gyp.pfc.activities.exception;

import java.util.Date;

/**
 * Exception for when a date is duplicated
 * 
 * @author Alvaro
 * 
 */
public class DuplicatedDateException extends Exception {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	/** The duplicated date */
	private final Date duplicated;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new exception specifying the duplicated date
	 * 
	 * @param duplicated
	 *            the duplicated date
	 */
	public DuplicatedDateException(Date duplicated) {
		super();
		this.duplicated = duplicated;
	}

	// Public --------------------------------------------------------

	public Date getDuplicated() {
		return duplicated;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
