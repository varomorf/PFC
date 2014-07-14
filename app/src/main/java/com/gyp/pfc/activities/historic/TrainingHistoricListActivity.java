/**
 * 
 */
package com.gyp.pfc.activities.historic;

import java.util.Collections;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.base.BaseListActivity;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.adapters.TrainingHistoricListViewAdapter;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;

/**
 * Activity for listing all user's training historic
 * 
 * @author Alvaro
 * 
 */
public class TrainingHistoricListActivity extends BaseListActivity implements ExerciseConstants {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Helper to be used */
	private BaseActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new BaseActivityHelper(this);

		setContentView(R.layout.no_search_list);
		setListAdapter(new TrainingHistoricListViewAdapter(this, getSortedHistorics()));
		registerForContextMenu(getListView());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		((TrainingHistoricListViewAdapter) getListAdapter()).setData(getSortedHistorics());
		((TrainingHistoricListViewAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@Override
	protected void doDelete(int position) {
		// get selected historic from adapter and call helper with positive
		// action
		TrainingHistoric historic = (TrainingHistoric) getListAdapter().getItem(position);
		h.deleteWithDialog(R.string.assureHistoricDeletion, deletionAction(historic));
	}

	@Override
	protected void doEdit(int position) {
		TrainingHistoric historic = (TrainingHistoric) getListAdapter().getItem(position);
		Intent intent = new Intent(this, EditTrainingHistoricActivity.class);
		intent.putExtra(SELECTED_HISTORIC, historic);
		startActivityForResult(intent, EDIT_HISTORIC);
	}

	// Private -------------------------------------------------------

	/**
	 * Creates an {@link OnClickListener} that on click will delete the passed
	 * historic from DB, refresh the adapter and show a toast to the user
	 * 
	 * @param historic
	 *            the historic to be deleted
	 * @return the {@link OnClickListener}
	 */
	private OnClickListener deletionAction(final TrainingHistoric historic) {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// delete historic on DB
				getHelper().getTrainingHistoricDao().delete(historic);
				// remove historic from adapter
				TrainingHistoricListViewAdapter adapter = (TrainingHistoricListViewAdapter) getListAdapter();
				adapter.remove(historic);
				// refresh the adapter to update UI
				adapter.notifyDataSetChanged();
				// show deletion message
				Toast.makeText(getApplicationContext(),
						historic.getTraining().getName() + " " + getString(R.string.deleteMessage),
						Toast.LENGTH_SHORT).show();
			}
		};
	}

	/**
	 * Returns the list of all historics sorted by date from latest to soonest
	 * 
	 * @return the list of all historics sorted
	 */
	private List<TrainingHistoric> getSortedHistorics() {
		List<TrainingHistoric> historic = getHelper().getTrainingHistoricDao().queryForAll();
		Collections.sort(historic, Collections.reverseOrder());
		return historic;
	}

	// Inner classes -------------------------------------------------

}
