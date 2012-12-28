package com.gyp.pfc.activities.exercise;

import java.util.List;

import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.ExerciseListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing exercises and interacting with them
 * 
 * @author Alvaro
 * 
 */
public class ExerciseListActivity extends
		OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_list);
		List<Exercise> exercises = getHelper().getExerciseDao().queryForAll();
		setListAdapter(new ExerciseListViewAdapter(this,
				R.layout.exercise_list_item, exercises));
		// TODO
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
