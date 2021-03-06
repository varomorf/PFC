/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.SharedPreferences;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.biometric.UserData;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.gyp.pfc.sharing.FileSharingName;
import com.xtremelabs.robolectric.tester.android.content.TestSharedPreferences;

/**
 * Tests for the {@link ShowUserBiometricDataActivity} activity
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class ShowUserBiometricDataActivityTest extends BaseWeightActivityTest {

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
	public void shouldShowUserDataFromConfiguration() throws IOException {
		// GIVEN
		// there is a weight
		Weight w = new Weight();
		w.setWeight(50d);
		weightDao.create(w);
		// set preferences values
		prepareTestUserData();
		// WHEN
		// activity is started
		createActivity();
		// THEN
		assertViewText(R.id.userDataShowAge, "27");
		assertViewText(R.id.userDataShowSex, activity.getString(R.string.woman));
		assertViewText(R.id.userDataShowHeight, "1,63 m.");
		assertViewText(R.id.userDataShowWeight, "50,00 Kg.");
		assertViewText(R.id.userDataShowBMI, "18,82");
	}

	@Test
	public void shouldFillWithZeroIfNoWeightPresent() {
		// GIVEN
		// set preferences values
		prepareTestUserData();
		// WHEN
		// activity is started
		createActivity();
		// THEN
		assertViewText(R.id.userDataShowWeight, "0,00 Kg.");
		assertViewText(R.id.userDataShowBMI, "0,00");
	}

	@Test
	public void shouldStartEditActivityWhenEditButtonIsPressed() {
		// GIVEN
		// WHEN
		// activity is started
		createActivity();
		// click on edit button
		clickOn(activity.findViewById(R.id.editButton));
		// THEN
		assertAndReturnNextActivity(EditUserDataActivity.class);
		assertTrue("Applicatoin should be finishing", activity.isFinishing());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ShowUserBiometricDataActivity();
	}

	// Private -------------------------------------------------------

	private void prepareTestUserData() {
		TestSharedPreferences preferences = (TestSharedPreferences) activity.getSharedPreferences(
				FileSharingName.USER_DATA.getFileName(), 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(UserData.AGE_KEY, 27);
		editor.putBoolean(UserData.SEX_IS_MAN_KEY, false);
		editor.putInt(UserData.HEIGHT_KEY, 163);
		editor.commit();
	}

	// Inner classes -------------------------------------------------

}
