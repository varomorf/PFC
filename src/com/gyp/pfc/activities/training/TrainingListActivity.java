package com.gyp.pfc.activities.training;

import java.util.List;

import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.TrainingAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Training;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing the trainings available on the
 * 
 * @author Alvaro
 * 
 */
public class TrainingListActivity extends
		OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entity_list);

		List<Training> trainings = getHelper().getTrainingDao().queryForAll();
		setListAdapter(new TrainingAdapter(this, trainings));
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
