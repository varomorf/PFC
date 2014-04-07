package com.gyp.pfc.activities;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	protected abstract Activity newActivity();

	protected void assertCRUDMenu(Menu menu) {
		assertNotNull(menu);
		assertThat(menu.getItem(0).getTitle().toString(), is(DELETE));
		assertThat(menu.getItem(1).getTitle().toString(), is(EDIT));
	}

	protected void createActivity() {
		activity.callOnCreate(null);
	}

	protected void assertToastText(int id) {
		CharSequence text = activity.getApplicationContext().getResources().getText(id);
		assertThat(ShadowToast.getTextOfLatestToast(), is(text));
	}

	protected void assertTitleOfChild(int pos, String text) {
		assertItemTitle(getItemFromListView(pos), text);
	}

	protected void assertTextOfListChild(int pos, int id, String text) {
		View item = getItemFromListView(pos);
		assertItemText(item, id, text);
	}

	protected void assertItemText(View item, int id, String text) {
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

	protected void clickOnListItem(int itemIndex) {
		ListView listView = (ListView) activity.findViewById(android.R.id.list);
		shadowOf(listView).performItemClick(itemIndex);
	}

	protected void longClickOnListItem(int itemIndex) {
		View item = getItemFromListView(itemIndex);
		item.performLongClick();
	}

	protected void clickOnListItemButton(int itemIndex, int buttonId) {
		View item = getItemFromListView(itemIndex);
		clickOn(item.findViewById(buttonId));
	}

	@SuppressWarnings("rawtypes")
	protected Intent assertAndReturnNextActivity(Class activityClass) {
		Intent next = activity.getNextStartedActivity();
		assertNotNull(next);
		assertEquals(activityClass.getName(), next.getComponent().getClassName());
		return next;
	}

	protected void assertViewText(int id, String expected) {
		View view = activity.findViewById(id);
		String text = UIUtils.getTextFromUI(view);
		assertThat(text, is(expected));
	}

	protected void assertViewTextIsNot(int id, String expected) {
		View view = activity.findViewById(id);
		String text = UIUtils.getTextFromUI(view);
		assertThat(text, is(not(expected)));
	}

	protected void assertAlertDialogText(int id) {
		AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		assertNotNull("There wasn't even a dialog", dialog);
		ShadowAlertDialog shadowDialog = shadowOf(dialog);
		assertEquals("Dialog text does not match", activity.getText(id), shadowDialog.getMessage());
	}

	protected void enterText(int id, String text) {
		EditText edit = (EditText) activity.findViewById(id);
		edit.setText(text);
	}

	protected void enterText(int id, double text) {
		EditText edit = (EditText) activity.findViewById(id);
		edit.setText(Double.toString(text));
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
