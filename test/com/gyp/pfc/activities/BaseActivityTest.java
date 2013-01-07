package com.gyp.pfc.activities;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowToast;

/**
 * Base class to gather common functionality for Activity testing
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseActivityTest {

	// Constants -----------------------------------------------------

	private static final String EDIT = "@string/label_edit";
	private static final String DELETE = "@string/label_delete";
	// Attributes ----------------------------------------------------
	protected ShadowActivity activity;
	protected Activity realActivity;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	public void before() {
		realActivity = newActivity();
		activity = shadowOf(realActivity);
		OpenHelperManager.getHelper(realActivity, DatabaseHelper.class);
	}

	public void assertCRUDMenu(Menu menu) {
		assertNotNull(menu);
		assertThat(menu.getItem(0).getTitle().toString(), is(DELETE));
		assertThat(menu.getItem(1).getTitle().toString(), is(EDIT));
	}

	public void createActivity() {
		activity.callOnCreate(null);
	}

	public void assertToastText(int id) {
		CharSequence text = activity.getApplicationContext().getResources()
				.getText(id);
		assertThat(ShadowToast.getTextOfLatestToast(), is(text));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	protected abstract Activity newActivity();
	
	protected void assertTitleOfChild(int pos, String text){
		assertItemTitle(getItemFromListView(pos), text);
	}
	
	protected void assertTextOfListChild(int pos, int id, String text){
		View item = getItemFromListView(pos);
		assertItemText(item, id, text);
	}
	
	protected void assertItemText(View item, int id, String text){
		TextView textView = (TextView) item.findViewById(id);
		assertThat(textView.getText().toString(), is(text));
	}

	protected void assertItemTitle(View item, String text) {
		assertItemText(item, R.id.title, text);
	}

	protected View getItemFromListView(int index) {
		ListView listView = (ListView) activity.findViewById(android.R.id.list);
		return listView.getChildAt(index);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
