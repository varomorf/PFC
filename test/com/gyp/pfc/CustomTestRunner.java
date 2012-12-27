package com.gyp.pfc;

import java.io.File;

import org.junit.runners.model.InitializationError;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Custom runner
 * 
 * @author Alvaro
 * 
 */
public class CustomTestRunner extends RobolectricTestRunner {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	public CustomTestRunner(Class testClass) throws InitializationError {
		// defaults to "AndroidManifest.xml", "res" in the current directory
		super(testClass, new File("../PersonalFitnessCoach"));
	}
	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
