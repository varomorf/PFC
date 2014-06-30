/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.RadioButton;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.domain.biometric.UserData;
import com.gyp.pfc.sharing.FileSharingName;
import com.xtremelabs.robolectric.tester.android.content.TestSharedPreferences;

/**
 * Test for {@link EditUserDataActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class EditUserDataActivityTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldSaveDataToSharedPreferences() {
		// GIVEN

		// WHEN
		// activity is started
		createActivity();
		UIUtils.setTextToUI(activity.findViewById(R.id.userDataEditAge), "50");
		((RadioButton) activity.findViewById(R.id.radioMan)).setChecked(true);
		((RadioButton) activity.findViewById(R.id.radioWoman)).setChecked(false);
		UIUtils.setTextToUI(activity.findViewById(R.id.userDataEditHeight), "1.80");
		clickOn(activity.findViewById(R.id.okButton));
		// THEN
		SharedPreferences preferences = activity.getSharedPreferences(FileSharingName.USER_DATA.getFileName(), 0);
		assertEquals("User age should have been saved", 50, preferences.getInt(UserData.AGE_KEY, -1));
		assertTrue("User sex should have been saved", preferences.getBoolean(UserData.SEX_IS_MAN_KEY, false));
		assertEquals("User age should have been saved", 180, preferences.getInt(UserData.HEIGHT_KEY, -1));
	}

	@Test
	public void shouldLoadDataOnPreferences() {
		// GIVEN
		// set test data
		TestSharedPreferences preferences = (TestSharedPreferences) activity.getSharedPreferences(
				FileSharingName.USER_DATA.getFileName(), 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(UserData.AGE_KEY, 27);
		editor.putBoolean(UserData.SEX_IS_MAN_KEY, false);
		editor.putInt(UserData.HEIGHT_KEY, 163);
		editor.commit();
		// WHEN
		// activity is started
		createActivity();
		// THEN
		assertViewText(R.id.userDataEditAge, "27");
		assertTrue("Woman radio button should be checked",
				((RadioButton) activity.findViewById(R.id.radioWoman)).isChecked());
		assertViewText(R.id.userDataEditHeight, "163");
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditUserDataActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
