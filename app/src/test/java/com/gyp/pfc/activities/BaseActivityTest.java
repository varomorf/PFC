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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.matchers.ContainsMatcher;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowActivity.IntentForResult;
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

	@SuppressWarnings("rawtypes")
	protected void createActivityFromActivity(Class callingActivity) {
		activity.setCallingActivity(callingActivity);
		activity.callOnCreate(null);
	}

	protected void assertToastText(int id) {
		CharSequence text = getText(id);
		assertToastText(text);
	}

	protected CharSequence getText(int id) {
		return activity.getApplicationContext().getResources().getText(id);
	}

	protected void assertToastText(CharSequence text) {
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

	protected void assertListSize(int size) {
		assertListSize(size, android.R.id.list);
	}

	protected void assertListSize(int size, int listId) {
		ListView listView = (ListView) activity.findViewById(listId);
		assertThat("La lista no contiene el número de items esperados", listView.getChildCount(), is(size));
	}

	protected View getItemFromListView(int index) {
		return getItemFromListView(index, android.R.id.list);
	}

	protected View getItemFromListView(int index, int listId) {
		ListView listView = (ListView) activity.findViewById(listId);
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

	protected void clickOnListItemButton(int listId, int itemIndex, int buttonId) {
		View item = getItemFromListView(itemIndex, listId);
		clickOn(item.findViewById(buttonId));
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

	@SuppressWarnings("rawtypes")
	protected IntentForResult assertAndReturnNextActivityForResult(Class activityClass) {
		IntentForResult next = activity.getNextStartedActivityForResult();
		assertNotNull(next);
		assertEquals(activityClass.getName(), next.intent.getComponent().getClassName());
		return next;
	}

	protected void assertViewText(int id, String expected) {
		View view = activity.findViewById(id);
		String text = UIUtils.getTextFromUI(view);
		assertThat(text, is(expected));
	}

	protected void assertViewText(int id, int expected) {
		assertViewText(id, Integer.toString(expected));
	}

	protected void assertViewContaining(int id, String expected) {
		View view = activity.findViewById(id);
		String text = UIUtils.getTextFromUI(view);
		assertThat(text, contains(expected));
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

	protected void clickYesOnDialog() {
		AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		assertNotNull("There wasn't even a dialog", dialog);
		ShadowAlertDialog shadowDialog = shadowOf(dialog);
		Button button = shadowDialog.getButton(AlertDialog.BUTTON_POSITIVE);
		clickOn(button);
	}

	protected void enterText(int id, String text) {
		EditText edit = (EditText) activity.findViewById(id);
		edit.setText(text);
	}

	protected void enterText(int id, double text) {
		EditText edit = (EditText) activity.findViewById(id);
		edit.setText(Double.toString(text));
	}

	protected void enterText(int id, int text) {
		EditText edit = (EditText) activity.findViewById(id);
		edit.setText(Integer.toString(text));
	}

	/**
	 * Is the value contained into another value, as tested by the
	 * {@link java.lang.String#contains} invokedMethod?
	 */
	public static <T> org.hamcrest.Matcher<T> contains(T operand) {
		return ContainsMatcher.contains(operand);
	}
	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
