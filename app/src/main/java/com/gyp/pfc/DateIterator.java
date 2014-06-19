package com.gyp.pfc;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Date iterator.
 * </p>
 */
public class DateIterator implements Iterator<Date> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private final Calendar endFinal;
	private final Calendar spot;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Constructs a DateIterator that ranges from one date to another.
	 * 
	 * @param startFinal
	 *            start date (inclusive)
	 * @param endFinal
	 *            end date (not inclusive)
	 */
	public DateIterator(Date startFinal, Date endFinal) {
		super();
		this.endFinal = Calendar.getInstance();
		this.endFinal.setTime(endFinal);
		spot = Calendar.getInstance();
		spot.setTime(startFinal);
		spot.add(Calendar.DATE, -1);
	}

	// Public --------------------------------------------------------

	/**
	 * Has the iterator not reached the end date yet?
	 * 
	 * @return <code>true</code> if the iterator has yet to reach the end date
	 */
	public boolean hasNext() {
		return spot.before(endFinal);
	}

	/**
	 * Return the next calendar in the iteration
	 * 
	 * @return Object calendar for the next date
	 */
	public Date next() {
		if (spot.equals(endFinal)) {
			throw new NoSuchElementException();
		}
		spot.add(Calendar.DATE, 1);
		return spot.getTime();
	}

	/**
	 * Always throws UnsupportedOperationException.
	 * 
	 * @throws UnsupportedOperationException
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
