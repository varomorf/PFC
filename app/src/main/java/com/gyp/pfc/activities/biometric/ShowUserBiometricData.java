/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.biometric.UserData;
import com.gyp.pfc.data.domain.biometric.UserSex;
import com.gyp.pfc.sharing.FileSharingName;

/**
 * This activity will show the user's biometric data stored on the bio.yaml internal file.
 * 
 * If said file is not found, it will mean that no data was previously entered and so another activity for edition
 * will be started.
 * 
 * @author Alvaro
 * 
 */
public class ShowUserBiometricData extends Activity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_data_show);

		UserData user = getUserData();

		updateUi(user);
	}

	// Private -------------------------------------------------------

	/**
	 * Updates the UI with the data from the passed {@link UserData}
	 * 
	 * @param user
	 *            the {@link UserData} to be used
	 */
	private void updateUi(UserData user) {
		UIUtils.setTextToUI(findViewById(R.id.userDataShowAge), user.getAge().toString());
		UIUtils.setTextToUI(findViewById(R.id.userDataShowSex), getString(user.getSex().getStringId()));
		UIUtils.setTextToUI(findViewById(R.id.userDataShowHeight), user.getFormattedHeight());
		UIUtils.setTextToUI(findViewById(R.id.userDataShowWeight), user.getFormattedWeight());
		UIUtils.setTextToUI(findViewById(R.id.userDataShowBMI), user.getFormattedBMI());
	}

	/**
	 * Gets the user data from the {@link SharedPreferences}
	 * 
	 * @return the user data
	 */
	private UserData getUserData() {
		UserData user = new UserData();
		// get user data from preferences
		SharedPreferences preferences = getSharedPreferences(FileSharingName.USER_DATA.getFileName(), 0);
		user.setAge(preferences.getInt(UserData.AGE_KEY, UserData.DEFAULT_AGE));
		if (!preferences.getBoolean(UserData.SEX_IS_MAN_KEY, true)) {
			user.setSex(UserSex.WOMAN);
		}
		user.setHeight(preferences.getInt(UserData.HEIGHT_KEY, UserData.DEFAULT_HEIGHT));
		return user;
	}

	// Inner classes -------------------------------------------------

}
