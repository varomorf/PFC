package com.gyp.pfc.monitoring;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * Class for time monitoring parts of code for performance metrics
 * 
 * @author Alvaro
 * 
 */
public class TimeMonitor {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	/** The monitor that will keep track of the time */
	private static Monitor monitor;

	/**
	 * Starts the monitor. Use before the functionality to measure.
	 */
	public static void start() {
		monitor = MonitorFactory.start();
	}

	/**
	 * Stops the monitor. Use after the functionality to measure.
	 */
	public static void stop() {
		monitor.stop();
	}

	/**
	 * Returns a String with the resulting data of the monitoring. A load can be specified.
	 * 
	 * @param load
	 *            The load of the functionality monitored (number of cycle, of entities, etc.)
	 * @return the String with the resulting data of the monitoring
	 */
	public static String getDataFor(long load) {
		StringBuilder sb = new StringBuilder().append("Monitoring : ").append(monitor.getLastValue());
		if (load != 0) {
			sb.append(" for ").append(load).append(" load.");
		}
		return sb.toString();
	}

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * TimeMonitor instances should not be created
	 */
	private TimeMonitor() {

	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
