/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.biometric.Weight;

/**
 * Tests for the {@link AddWeightActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class AddWeightActivityTest extends BaseWeightActivityTest {

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
	public void shouldAddWeightWithDateAndValue() {
		// GIVEN
		// no weights on DB
		assertTrue("There should be no weights on DB", weightDao.queryForAll().isEmpty());
		// WHEN
		// activity starts
		createActivity();
		// date is entered
		Date date = new Date();
		enterDate(date);
		// weight is entered
		UIUtils.setTextToUI(activity.findViewById(R.id.weightAddWeight), "75.5");
		// ok button is pressed
		clickOn(activity.findViewById(R.id.okButton));
		// THEN
		assertViewText(R.id.weightAddDate, TimeUtils.formatDate(date));
		// a Weight is created with the entered data
		assertEquals("There should be one weight", 1, weightDao.queryForAll().size());
		Weight weight = weightDao.queryForAll().get(0);
		assertEquals("Created weight should have correct weight", 75.5d, weight.getWeight(), 0);
		assertEquals("Created weight should have correct date", TimeUtils.formatDate(date),
				TimeUtils.formatDate(weight.getDate()));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new AddWeightActivity();
	}

	// Private -------------------------------------------------------

	private void enterDate(Date date) {
		// mock calls to onDateSet
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		((OnDateSetListener) realActivity).onDateSet(null, year, month, day);
	}

	// Inner classes -------------------------------------------------

}
