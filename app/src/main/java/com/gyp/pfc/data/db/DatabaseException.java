package com.gyp.pfc.data.db;

/**
 * Exceptions occurred on database scope
 * 
 * @author Alvaro
 * 
 */
public class DatabaseException extends RuntimeException {

	// Constants -----------------------------------------------------
	private static final long serialVersionUID = 5449357251496293706L;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Constructs a new {@code DatabaseException} that includes the current
	 * stack trace.
	 */
	public DatabaseException() {
		super();
	}

	/**
	 * Constructs a new {@code DatabaseException} with the current stack trace,
	 * the specified detail message and the specified cause.
	 * 
	 * @param detailMessage
	 *            the detail message for this exception.
	 * @param throwable
	 *            the cause of this exception.
	 */
	public DatabaseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	/**
	 * Constructs a new {@code DatabaseException} with the current stack trace
	 * and the specified detail message.
	 * 
	 * @param detailMessage
	 *            the detail message for this exception.
	 */
	public DatabaseException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * Constructs a new {@code DatabaseException} with the current stack trace
	 * and the specified cause.
	 * 
	 * @param throwable
	 *            the cause of this exception.
	 */
	public DatabaseException(Throwable throwable) {
		super(throwable);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
