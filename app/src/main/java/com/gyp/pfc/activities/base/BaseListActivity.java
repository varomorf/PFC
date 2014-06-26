/**
 * 
 */
package com.gyp.pfc.activities.base;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Base class for list activities
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.crud_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete:
			doDelete(info.position);
			return true;
		case R.id.edit:
			doEdit(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	/**
	 * Deletes the selected entity via a confirmation dialog
	 * 
	 * @param position
	 *            the position in the list of the selected entity
	 */
	protected abstract void doDelete(int position);

	/**
	 * Launches an intent for the edition of the entity on the specified
	 * position
	 * 
	 * @param position
	 *            the position in the list (starts on 0) of the entity to be
	 *            edited
	 */
	protected abstract void doEdit(int position);

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
