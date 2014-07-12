package com.gyp.pfc.monitoring;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

public class TimeMonitor {

	static private Monitor monitor;

	static public void start() {
		monitor = MonitorFactory.start();
	}

	static public void stop() {
		monitor.stop();
	}

	static public String getDataFor(long load) {
		StringBuilder sb = new StringBuilder().append("Monitoring : ").append(monitor.getLastValue());
		if (load != 0) {
			sb.append(" for ").append(load).append(" load.");
		}
		return sb.toString();
	}

}
