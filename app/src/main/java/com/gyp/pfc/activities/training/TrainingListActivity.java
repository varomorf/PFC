package com.gyp.pfc.activities.training;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
public class TrainingListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback for the delete button
	 * 
	 * @param view
	 */
	public void deleteButton(View view) {
		// get the training from item of button pressed
		Training training = getTrainingForClickedButtonListItem(view);
		// remove related TrainginExercises
		getHelper().getTrainingExerciseDao().delete(training.getExercises());
		// remove from DB
		getHelper().getTrainingDao().delete(training);
		// remove from UI
		getAdapter().remove(training);
		getAdapter().notifyDataSetChanged();
		// show deletion message
		Toast.makeText(getApplicationContext(), R.string.trainingDeleted, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Callback for the edit button
	 * 
	 * @param view
	 */
	public void editButton(View view) {
		// get the training from item of button pressed
		Training training = getTrainingForClickedButtonListItem(view);
		// prepare intent
		Intent intent = new Intent(this, AddTrainingActivity.class);
		// add training to intent
		intent.putExtra(AddTrainingActivity.TRAINING, training);
		// launch activity
		startActivity(intent);
	}

	/**
	 * Callback for the play button
	 * 
	 * @param view
	 */
	public void playButton(View view) {
		// get the training from item of button pressed
		Training training = getTrainingForClickedButtonListItem(view);
		// prepare intent
		Intent intent = new Intent(this, ExecuteTrainingActivity.class);
		// add training to intent
		intent.putExtra(AddTrainingActivity.TRAINING, training);
		// launch activity
		startActivity(intent);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entity_list);

		List<Training> trainings = getHelper().getTrainingDao().queryForAll();
		setListAdapter(new TrainingAdapter(this, trainings));
	}

	@Override
	protected void onResume() {
		super.onResume();
		// clear the list
		getAdapter().clear();
		// add all trainings
		for (Training training : getHelper().getTrainingDao().queryForAll()) {
			getAdapter().add(training);
		}
		// refresh UI
		getAdapter().notifyDataSetChanged();
	}

	// Private -------------------------------------------------------

	private int getIndexOfItemForPressedButton(View view) {
		// get position of item for which button was pressed
		ListView exerciseList = (ListView) findViewById(android.R.id.list);
		return exerciseList.indexOfChild((View) view.getParent());
	}

	private TrainingAdapter getAdapter() {
		return (TrainingAdapter) getListAdapter();
	}

	private Training getTrainingForClickedButtonListItem(View view) {
		// get index item of button pressed
		int index = getIndexOfItemForPressedButton(view);
		// get the training from the adapter
		return getAdapter().getItem(index);
	}

	// Inner classes -------------------------------------------------
}
