package com.gyp.pfc.activities.training;

import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity to add trainings and reference exercises to trainings.
 * 
 * @author Alvaro
 * 
 */
public class AddTrainingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.training_data);
	}
	
	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
