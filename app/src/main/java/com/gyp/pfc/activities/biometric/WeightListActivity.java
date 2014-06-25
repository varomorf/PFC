package com.gyp.pfc.activities.biometric;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.WeightListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing all weights on DB ordered from the latest.
 * 
 * The user can delete and edit the shown entries.
 * 
 * @author Alvaro
 */
public class WeightListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

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

		setContentView(R.layout.no_search_list);
		List<Weight> weights = getHelper().getWeightDao().queryForAll();
		Collections.sort(weights, Collections.reverseOrder());
		setListAdapter(new WeightListViewAdapter(this, weights));
		registerForContextMenu(getListView());
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
