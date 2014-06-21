package com.gyp.pfc.activities.exercise;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.ExerciseListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing exercises and interacting with them
 * 
 * @author Alvaro
 * 
 */
public class ExerciseListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------
	static final String SELECTED_EXERCISE = "exercise";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entity_list);
		List<Exercise> exercises = getHelper().getExerciseDao().queryForAll();
		setListAdapter(new ExerciseListViewAdapter(this, R.layout.exercise_list_item, exercises));
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.crud_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete:
			deleteExercise(info.position);
			return true;
		case R.id.edit:
			editExercise(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
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
		// launch intent
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// remove all exercises from adapter
		getAdapter().clear();
		// set list from DAO
		for (Exercise exercise : getHelper().getExerciseDao().queryForAll()) {
			getAdapter().add(exercise);
		}
		// refresh the adapter to update UI
		getAdapter().notifyDataSetChanged();
	}

	// Private -------------------------------------------------------
	private void deleteExercise(int position) {
		// get selected exercise from adapter
		Exercise exercise = (Exercise) getListAdapter().getItem(position);
		// delete exercise on DB
		getHelper().getExerciseDao().delete(exercise);
		// delete exercise on adapter
		getAdapter().remove(exercise);
		// refresh the adapter to update UI
		getAdapter().notifyDataSetChanged();
		// show deletion message
		Toast.makeText(getApplicationContext(), R.string.exerciseDeleted, Toast.LENGTH_SHORT).show();
	}

	private ExerciseListViewAdapter getAdapter() {
		return (ExerciseListViewAdapter) getListAdapter();
	}

	private void editExercise(int position) {
		// get selected exercise from adapter
		Exercise exercise = (Exercise) getListAdapter().getItem(position);
		// prepare intent for edition
		Intent intent = new Intent(getApplicationContext(), EditExerciseActivity.class);
		// pass selected exercise
		intent.putExtra(ExerciseListActivity.SELECTED_EXERCISE, exercise);
		// launch activity
		startActivity(intent);
	}
	// Inner classes -------------------------------------------------
}
