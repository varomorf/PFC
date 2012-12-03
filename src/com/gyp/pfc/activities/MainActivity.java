package com.gyp.pfc.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.MainListAdapter;
import com.gyp.pfc.data.db.DBManager;

public class MainActivity extends ListActivity {

	private MainListAdapter adapter;

	private String[] mainSectionsNames = { "Foods", "Meals", "Exercises",
			"Trainnings" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBManager.createInstance(getApplicationContext());
		adapter = new MainListAdapter(this, R.layout.main_list_item,
				mainSectionsNames);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
