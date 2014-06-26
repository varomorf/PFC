package com.gyp.pfc.activities.biometric;

import java.util.Collections;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.base.BaseListActivity;
import com.gyp.pfc.activities.constants.BiometricConstants;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.adapters.WeightListViewAdapter;
import com.gyp.pfc.data.domain.biometric.Weight;

/**
 * Activity for listing all weights on DB ordered from the latest.
 * 
 * The user can delete and edit the shown entries.
 * 
 * @author Alvaro
 */
public class WeightListActivity extends BaseListActivity implements BiometricConstants {

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
		// list of weights must be ordered
		setListAdapter(new WeightListViewAdapter(this, getSortedWeights()));
		// list items must have context menu
		registerForContextMenu(getListView());
	}

	@Override
	protected void doDelete(int position) {
		// get selected weight from adapter and call helper with positive action
		Weight weight = (Weight) getListAdapter().getItem(position);
		h.deleteWithDialog(R.string.assureWeightDeletion, deletionAction(weight));
	}

	@Override
	protected void doEdit(int position) {
		Weight weight = (Weight) getListAdapter().getItem(position);
		Intent intent = new Intent(this, EditWeightActivity.class);
		intent.putExtra(SELECTED_WEIGHT, weight);
		startActivityForResult(intent, EDIT_WEIGHT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		((WeightListViewAdapter) getListAdapter()).setData(getSortedWeights());
		((WeightListViewAdapter) getListAdapter()).notifyDataSetChanged();
	}

	// Private -------------------------------------------------------

	/**
	 * Returns all {@link Weight} entities on DB sorted descending by date
	 * 
	 * @return all {@link Weight} entities on DB sorted descending by date
	 */
	private List<Weight> getSortedWeights() {
		List<Weight> weights = getHelper().getWeightDao().queryForAll();
		Collections.sort(weights, Collections.reverseOrder());
		return weights;
	}

	/**
	 * Creates an {@link OnClickListener} that on click will delete the passed
	 * weight from DB, refresh the adapter and show a toast to the user
	 * 
	 * @param weight
	 *            the weight to be deleted
	 * @return the {@link OnClickListener}
	 */
	private OnClickListener deletionAction(final Weight weight) {
		return new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// delete weight on DB
				getHelper().getWeightDao().delete(weight);
				// remove weight from adapter
				WeightListViewAdapter adapter = (WeightListViewAdapter) getListAdapter();
				adapter.remove(weight);
				// refresh the adapter to update UI
				adapter.notifyDataSetChanged();
				// show deletion message
				Toast.makeText(getApplicationContext(),
						getString(R.string.theWeight) + " " + getString(R.string.deleteMessage),
						Toast.LENGTH_SHORT).show();
			}
		};
	}

	// Inner classes -------------------------------------------------

}
