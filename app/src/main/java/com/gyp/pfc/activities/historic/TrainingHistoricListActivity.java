/**
 * 
 */
package com.gyp.pfc.activities.historic;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.TrainingHistoricListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing all user's training historic
 * 
 * @author Alvaro
 * 
 */
public class TrainingHistoricListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

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
		List<TrainingHistoric> historic = getHelper().getTrainingHistoricDao().queryForAll();
		Collections.sort(historic, Collections.reverseOrder());
		setListAdapter(new TrainingHistoricListViewAdapter(this, historic));
		// registerForContextMenu(getListView());
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
