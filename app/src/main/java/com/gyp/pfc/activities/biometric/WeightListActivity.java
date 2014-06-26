package com.gyp.pfc.activities.biometric;

import java.util.Collections;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.adapters.WeightListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing all weights on DB ordered from the latest.
 * 
 * The user can delete and edit the shown entries.
 * 
 * @author Alvaro
 */
public class WeightListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Helper to be used */
	private BaseActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

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
			deleteWeight(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new BaseActivityHelper(this);

		setContentView(R.layout.no_search_list);
		// list of weights must be ordered
		List<Weight> weights = getHelper().getWeightDao().queryForAll();
		Collections.sort(weights, Collections.reverseOrder());
		setListAdapter(new WeightListViewAdapter(this, weights));
		// list items must have context menu
		registerForContextMenu(getListView());
	}

	// Private -------------------------------------------------------

	/**
	 * Deletes the selected weight via a confirmation dialog
	 * 
	 * @param position
	 *            the position in the list of the selected weight
	 */
	private void deleteWeight(int position) {
		// get selected historic from adapter and call helper with positive
		// action
		Weight weight = (Weight) getListAdapter().getItem(position);
		h.deleteWithDialog(R.string.assureWeightDeletion, deletionAction(weight));
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
