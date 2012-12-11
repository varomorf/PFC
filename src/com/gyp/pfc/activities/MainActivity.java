package com.gyp.pfc.activities;

import android.os.Bundle;
import android.view.Menu;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.MainListAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Main activity of the application. Holds the list of the different actions.
 * 
 * @author Alvaro
 * 
 */
public class MainActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------
	private static final String[] MAIN_SECTIONS_NAMES = { "Foods", "Meals",
			"Exercises", "Trainnings" };

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// set the list adapter
		MainListAdapter adapter = new MainListAdapter(this,
				R.layout.main_list_item, MAIN_SECTIONS_NAMES);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
