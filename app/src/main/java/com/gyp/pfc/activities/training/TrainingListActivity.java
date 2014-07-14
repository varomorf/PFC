package com.gyp.pfc.activities.training;

import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.adapters.TrainingAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.manager.TrainingManager;
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

	/** Helper to be used */
	private BaseActivityHelper h;

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
		h.deleteWithDialog(R.string.assureTrainingDeletion, deletionAction(training));
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
		h = new BaseActivityHelper(this);
		setContentView(R.layout.entity_list);

		List<Training> trainings = getHelper().getTrainingDao().queryForEq("executable", true);
		setListAdapter(new TrainingAdapter(this, trainings));
	}

	@Override
	protected void onResume() {
		super.onResume();
		// clear the list
		getAdapter().clear();
		// add all trainings
		for (Training training : TrainingManager.it().getAllTrainings()) {
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

	/**
	 * Creates an {@link OnClickListener} that on click will delete the passed training from DB, refresh the
	 * adapter and show a toast to the user
	 * 
	 * @param training
	 *            the {@link Training} to be deleted
	 * @return the {@link OnClickListener}
	 */
	private OnClickListener deletionAction(final Training training) {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
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
		};
	}

	// Inner classes -------------------------------------------------
}
