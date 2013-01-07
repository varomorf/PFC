package com.gyp.pfc;

import android.annotation.SuppressLint;

/**
 * Utility methods to handle time
 * 
 * @author Alvaro
 * 
 */
public final class TimeUtils {

	// Constants -----------------------------------------------------

	public static final int SECONDS_PER_MINUTE = 60;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------
	/**
	 * Returns the number of integer minutes from the passed seconds
	 * 
	 * @param seconds
	 *            The seconds from which to calculate
	 * @return the number of integer minutes
	 */
	public static int minutesFromSeconds(int seconds) {
		return seconds / SECONDS_PER_MINUTE;
	}

	/**
	 * <p>
	 * Returns the number of seconds after removing the seconds corresponding to
	 * integer minutes.
	 * </p>
	 * 
	 * <p>
	 * Eg. 90 seconds -> 1 minute and 30 seconds, so it will return 30
	 * </p>
	 * 
	 * @param seconds
	 *            The seconds from which to calculate
	 * @return The number of seconds
	 */
	public static int restingSecondsFromSeconds(int seconds) {
		return seconds % SECONDS_PER_MINUTE;
	}

	/**
	 * <p>
	 * Calculates the total of seconds from adding the passed minutes converted
	 * to seconds and the passed seconds
	 * </p>
	 * 
	 * @param minutes
	 *            The minutes to be added
	 * @param seconds
	 *            The seconds to be added
	 * @return The total seconds
	 */
	public static int secondsFromMinutesAndSeconds(int minutes, int seconds) {
		return (minutes * SECONDS_PER_MINUTE) + seconds;
	}

	/**
	 * Overload to use strings instead of integers
	 * 
	 * @param minutes
	 *            The minutes to be added
	 * @param seconds
	 *            The seconds to be added
	 * @return The total seconds
	 */
	public static int secondsFromMinutesAndSeconds(String minutes,
			String seconds) {
		int mins = Integer.parseInt(minutes);
		int secs = Integer.parseInt(seconds);
		return secondsFromMinutesAndSeconds(mins, secs);
	}

	/**
	 * Formats the passed seconds in mm:ss time format
	 * 
	 * @param seconds
	 *            The number of seconds to be formatted
	 * @return The formatted time
	 */
	@SuppressLint("DefaultLocale")
	public static String formatTime(int seconds) {
		int mins = minutesFromSeconds(seconds);
		int secs = restingSecondsFromSeconds(seconds);
		return String.format("%02d:%02d", mins, secs);
	}

	// Constructors --------------------------------------------------
	private TimeUtils() {
		// NOOP
	}
	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
