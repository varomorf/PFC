/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.helpers.UserHelper;
import com.gyp.pfc.data.domain.biometric.UserData;
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
public class ShowUserBiometricDataActivity extends Activity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback method for the editButton
	 * 
	 * @param view
	 *            the editButton
	 */
	public void editButton(View view) {
		finish();
		startActivity(new Intent(this, EditUserDataActivity.class));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getSharedPreferences(FileSharingName.USER_DATA.getFileName(), 0).getAll().isEmpty()) {
			// if no preferences go directly to edit
			editButton(null);
		}
		setContentView(R.layout.user_data_show);

		updateUi(new UserHelper(this).getUserData());
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

	// Inner classes -------------------------------------------------

}
