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
import com.gyp.pfc.data.domain.biometric.Weight;
import com.gyp.pfc.data.domain.manager.WeightManager;
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

		SharedPreferences preferences = getSharedPreferences(FileSharingName.USER_DATA.getFileName(), 0);
		UserData user = new UserData();
		user.setAge(preferences.getInt(UserData.AGE_KEY, UserData.DEFAULT_AGE));
		if (!preferences.getBoolean(UserData.SEX_IS_MAN_KEY, true)) {
			user.setSex(UserSex.WOMAN);
		}
		user.setHeight(preferences.getInt(UserData.HEIGHT_KEY, UserData.DEFAULT_HEIGHT));

		UIUtils.setTextToUI(findViewById(R.id.userDataShowAge), user.getAge().toString());
		UIUtils.setTextToUI(findViewById(R.id.userDataShowSex), getString(user.getSex().getStringId()));
		UIUtils.setTextToUI(findViewById(R.id.userDataShowHeight), user.getFormattedHeight());
		Weight lastWeight = WeightManager.it().getLastWeight();
		if (null != lastWeight) {
			UIUtils.setTextToUI(findViewById(R.id.userDataShowWeight), lastWeight.getFormattedWeight());
		}
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
