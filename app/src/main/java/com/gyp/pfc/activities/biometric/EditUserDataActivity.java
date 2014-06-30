/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.helpers.UserHelper;
import com.gyp.pfc.data.domain.biometric.UserData;
import com.gyp.pfc.data.domain.biometric.UserSex;
import com.gyp.pfc.sharing.FileSharingName;

/**
 * Activity for editing the {@link UserData} in the {@link SharedPreferences}
 * 
 * @author Alvaro
 * 
 */
public class EditUserDataActivity extends Activity {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback method for the okButton
	 * 
	 * @param view
	 *            the okButton
	 */
	public void okButton(View view) {
		SharedPreferences preferences = getSharedPreferences(FileSharingName.USER_DATA.getFileName(), 0);
		SharedPreferences.Editor editor = preferences.edit();
		// get and convert data from UI
		int age = Integer.parseInt(UIUtils.getTextFromUI(findViewById(R.id.userDataEditAge)));
		boolean man = ((RadioButton) findViewById(R.id.radioMan)).isChecked();
		double heightValue = Double.parseDouble(UIUtils.getTextFromUI(findViewById(R.id.userDataEditHeight)));
		int height = new Double(heightValue * 100).intValue();
		// put data on preferences
		editor.putInt(UserData.AGE_KEY, age);
		editor.putBoolean(UserData.SEX_IS_MAN_KEY, man);
		editor.putInt(UserData.HEIGHT_KEY, height);
		// save edits
		editor.commit();
		// end activity
		finish();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_data_edit);

		UserData user = new UserHelper(this).getUserData();
		UIUtils.setTextToUI(findViewById(R.id.userDataEditAge), user.getAge().toString());
		UIUtils.setTextToUI(findViewById(R.id.userDataEditHeight), user.getHeight().toString());
		if (user.getSex() != UserSex.MAN) {
			((RadioButton) findViewById(R.id.radioWoman)).setChecked(true);
		}
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
