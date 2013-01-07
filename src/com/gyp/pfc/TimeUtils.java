package com.gyp.pfc;

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
	public static int minutesFromSeconds(int seconds){
		return seconds / SECONDS_PER_MINUTE;
	}
	
	public static int restingSecondsFromSeconds(int seconds){
		return seconds % SECONDS_PER_MINUTE;
	}
	
	public static int secondsFromMinutesAndSeconds(int minutes, int seconds){
		return (minutes * SECONDS_PER_MINUTE) + seconds;
	}
	
	public static int secondsFromMinutesAndSeconds(String minutes, String seconds){
		int mins = Integer.parseInt(minutes);
		int secs = Integer.parseInt(seconds);
		return secondsFromMinutesAndSeconds(mins, secs);
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
