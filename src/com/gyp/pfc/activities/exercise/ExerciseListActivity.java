package com.gyp.pfc.activities.exercise;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

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
	static final String SELECTED_EXERCISE = "exercise";

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
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.crud_context_menu, menu);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// get clicked exercise
		Exercise exercise = (Exercise) l.getItemAtPosition(position);
		// create intent for details view
		Intent intent = new Intent(getApplicationContext(), ExerciseDetailsActivity.class);
		// put selected exercise on intent
		intent.putExtra(SELECTED_EXERCISE, exercise);
		//launch intent
		startActivity(intent);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
