/**
 * 
 */
package com.gyp.pfc.activities.helpers;

import android.app.Activity;
import android.content.SharedPreferences;

import com.gyp.pfc.data.domain.biometric.UserData;
import com.gyp.pfc.data.domain.biometric.UserSex;
import com.gyp.pfc.sharing.FileSharingName;

/**
 * Helper class for {@link UserData} related actions
 * 
 * @author Alvaro
 * 
 */
public class UserHelper extends BaseActivityHelper {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link UserHelper} specifying its activity
	 * 
	 * @param activity
	 *            the activity for the new {@link UserHelper}
	 */
	public UserHelper(Activity activity) {
		super(activity);
	}

	// Public --------------------------------------------------------

	/**
	 * Gets the user data from the {@link SharedPreferences}
	 * 
	 * @return the user data
	 */
	public UserData getUserData() {
		UserData user = new UserData();
		// get user data from preferences
		SharedPreferences preferences = activity.getSharedPreferences(FileSharingName.USER_DATA.getFileName(), 0);
		user.setAge(preferences.getInt(UserData.AGE_KEY, UserData.DEFAULT_AGE));
		if (!preferences.getBoolean(UserData.SEX_IS_MAN_KEY, true)) {
			user.setSex(UserSex.WOMAN);
		}
		user.setHeight(preferences.getInt(UserData.HEIGHT_KEY, UserData.DEFAULT_HEIGHT));
		return user;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
