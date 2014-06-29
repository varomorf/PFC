package com.gyp.pfc.activities.exercise;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.base.BaseListActivity;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.adapters.ExerciseListViewAdapter;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.manager.ExerciseManager;

/**
 * Activity for listing exercises and interacting with them
 * 
 * @author Alvaro
 * 
 */
public class ExerciseListActivity extends BaseListActivity implements ExerciseConstants {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entity_list);
		List<Exercise> exercises = ExerciseManager.it().getAllExercises();
		setListAdapter(new ExerciseListViewAdapter(this, R.layout.exercise_list_item, exercises));
		registerForContextMenu(getListView());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// get clicked exercise
		Exercise exercise = (Exercise) l.getItemAtPosition(position);
		if (getIntent() != null && getIntent().getBooleanExtra(RETURN_EXERCISE, false)) {
			returnResult(exercise);
		} else {
			showExerciseDetails(exercise);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// remove all exercises from adapter
		getAdapter().clear();
		// set list from DAO
		for (Exercise exercise : ExerciseManager.it().getAllExercises()) {
			getAdapter().add(exercise);
		}
		// refresh the adapter to update UI
		getAdapter().notifyDataSetChanged();
	}

	@Override
	protected void doDelete(int position) {
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

	@Override
	protected void doEdit(int position) {
		// get selected exercise from adapter
		Exercise exercise = (Exercise) getListAdapter().getItem(position);
		// prepare intent for edition
		Intent intent = new Intent(getApplicationContext(), EditExerciseActivity.class);
		// pass selected exercise
		intent.putExtra(ExerciseListActivity.SELECTED_EXERCISE, exercise);
		// launch activity
		startActivity(intent);
	}

	// Private -------------------------------------------------------

	private ExerciseListViewAdapter getAdapter() {
		return (ExerciseListViewAdapter) getListAdapter();
	}

	/**
	 * Starts the activity for showing exercise details with the passed exercise
	 * 
	 * @param exercise
	 *            the exercise to be shown
	 */
	private void showExerciseDetails(Exercise exercise) {
		// create intent for details view
		Intent intent = new Intent(getApplicationContext(), ExerciseDetailsActivity.class);
		// put selected exercise on intent
		intent.putExtra(SELECTED_EXERCISE, exercise);
		// launch intent
		startActivity(intent);
	}

	/**
	 * Returns the passed exercise as result and finished this activity
	 * 
	 * @param exercise
	 *            the exercise to be returned as result
	 */
	private void returnResult(Exercise exercise) {
		// prepare intent for returning selected exercise
		Intent intent = new Intent();
		// put the selected exercise on the intent
		intent.putExtra(SELECTED_EXERCISE, exercise);
		// return result and finish
		setResult(SELECT_EXERCISE, intent);
		finish();
	}

	// Inner classes -------------------------------------------------
}
