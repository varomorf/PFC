package com.gyp.pfc.data.domain.exception;

/**
 * Exception to be used when the name provided for an entity is not valid
 * 
 * @author Alvaro
 * 
 */
public class EntityNameException extends Exception {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	/**
	 * The id of the error message
	 */
	private final int exeptionMessageId;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	
	/**
	 * Creates a new exception specifying the error message id
	 * 
	 * @param exeptionMessageId
	 *            the id of the error message
	 */
	public EntityNameException(int exeptionMessageId) {
		super();
		this.exeptionMessageId = exeptionMessageId;
	}

	// Public --------------------------------------------------------

	/**
	 * @return the exeptionMessageId
	 */
	public int getExeptionMessageId() {
		return exeptionMessageId;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
