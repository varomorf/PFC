/**
 * 
 */
package com.gyp.pfc.activities.food;

import android.widget.EditText;

/**
 * Exception for mandatory fields that have not been filled
 * 
 * @author Alvaro
 * 
 */
public class MandatoryFieldNotFilledException extends RuntimeException {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	/** The mandatory field */
	private final EditText field;

	/** The error message related to the mandatory field */
	private final String errorMessage;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new exception specifying the mandatory field and error message
	 * 
	 * @param field
	 *            the mandatory field
	 * @param errorMessage
	 *            the errorMessage
	 */
	public MandatoryFieldNotFilledException(EditText field, String errorMessage) {
		super(errorMessage);
		this.field = field;
		this.errorMessage = errorMessage;

		// Public --------------------------------------------------------
	}

	/**
	 * @return the field
	 */
	public EditText getField() {
		return field;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
