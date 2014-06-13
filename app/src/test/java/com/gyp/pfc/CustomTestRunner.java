package com.gyp.pfc;

import java.io.File;

import org.junit.runners.model.InitializationError;

import com.gyp.pfc.shadows.ShadowDragSortListView;
import com.xtremelabs.robolectric.Robolectric;
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
	@SuppressWarnings("rawtypes")
	public CustomTestRunner(Class testClass) throws InitializationError {
		// defaults to "AndroidManifest.xml", "res" in the current directory
		super(testClass, new File("."));
		addClassOrPackageToInstrument("com.mobeta.android.dslv.DragSortListView");
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void bindShadowClasses() {
		super.bindShadowClasses();
		Robolectric.bindShadowClass(ShadowDragSortListView.class);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
