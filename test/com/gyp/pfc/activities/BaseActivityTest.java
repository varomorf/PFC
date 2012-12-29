package com.gyp.pfc.activities;

import static com.xtremelabs.robolectric.Robolectric.*;
import android.app.Activity;

import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

/**
 * Base class to gather common functionality for Activity testing
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	protected ShadowActivity activity;
	protected Activity realActivity;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	public void before() {
		realActivity = createActivity();
		activity = shadowOf(realActivity);
		OpenHelperManager.getHelper(realActivity, DatabaseHelper.class);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	protected abstract Activity createActivity();

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
