/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.constants.BiometricConstants;
import com.gyp.pfc.data.domain.biometric.Weight;

/**
 * Tests for the {@link AddWeightActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class EditWeightActivityTest extends BaseWeightActivityTest implements BiometricConstants {

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
	public void shouldEditPassedWeight() {
		// GIVEN
		// one weight
		Weight weight = new Weight();
		weight.setDate(new Date());
		weight.setWeight(75.5d);
		weightDao.create(weight);
		assertEquals("There should be only one weight", 1, weightDao.queryForAll().size());
		// pass weight via intent
		Intent intent = new Intent();
		intent.putExtra(SELECTED_WEIGHT, weight);
		activity.setIntent(intent);
		// WHEN
		// activity starts
		createActivity();
		// THEN
		// weight data is preloaded
		assertViewText(R.id.weightAddDate, TimeUtils.formatDate(weight.getDate()));
		assertViewText(R.id.weightAddWeight, "75.5");
		// WHEN
		// weight is edited
		UIUtils.setTextToUI(activity.findViewById(R.id.weightAddWeight), "80");
		// ok button is pressed
		clickOn(activity.findViewById(R.id.okButton));
		// THEN
		// weight has been edited is created with the entered data
		assertEquals("There should be one weight", 1, weightDao.queryForAll().size());
		weightDao.refresh(weight);
		assertEquals("Created weight should have correct weight", 80d, weight.getWeight(), 0);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditWeightActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
