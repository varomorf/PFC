/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.constants.BiometricConstants;
import com.gyp.pfc.data.domain.biometric.Weight;

/**
 * Activity for editing an existing weight on the application.
 * 
 * The users will only be able to edit the weight value.
 * 
 * @author Alvaro
 * 
 */
public class EditWeightActivity extends AddWeightActivity implements BiometricConstants {

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
		// remove date button
		ImageButton button = (ImageButton) findViewById(R.id.weightAddDateButton);
		((LinearLayout) findViewById(R.id.weightAddLayoutDate)).removeView(button);

		// get Weight from intent
		weight = (Weight) getIntent().getExtras().get(SELECTED_WEIGHT);
		// fill data
		UIUtils.setTextToUI(findViewById(R.id.weightAddDate), TimeUtils.formatDate(weight.getDate()));
		UIUtils.setTextToUI(findViewById(R.id.weightAddWeight), weight.getWeight().toString());
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
