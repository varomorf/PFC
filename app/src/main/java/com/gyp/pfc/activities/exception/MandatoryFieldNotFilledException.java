/**
 * 
 */
package com.gyp.pfc.activities.exception;

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

	/** The mandatory field's view id */
	private final int fieldId;

	/** The error message related to the mandatory field */
	private final String errorMessage;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new exception specifying the mandatory field and error message
	 * 
	 * @param viewId
	 *            the mandatory field's id
	 * @param errorMessage
	 *            the errorMessage
	 */
	public MandatoryFieldNotFilledException(int viewId, String errorMessage) {
		super(errorMessage);
		this.fieldId = viewId;
		this.errorMessage = errorMessage;

		// Public --------------------------------------------------------
	}

	/**
	 * @return the field id
	 */
	public int getFieldId() {
		return fieldId;
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
