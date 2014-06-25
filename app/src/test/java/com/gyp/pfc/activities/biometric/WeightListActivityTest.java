package com.gyp.pfc.activities.biometric;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.biometric.Weight;

/**
 * Tests for the {@link WeightListActivity}
 * 
 * @author alfergon
 * 
 */
@RunWith(CustomTestRunner.class)
public class WeightListActivityTest extends BaseWeightActivityTest {

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
	public void shouldListAllWeightsOrderedByDateFromTheLatest() {
		// GIVEN
		// three dates with different dates on db
		createWeight(new Date(), 80d);
		createWeight(DateUtils.addDays(new Date(), 1), 75d);
		createWeight(DateUtils.addDays(new Date(), -1), 85d);
		// WHEN
		// activity is started
		createActivity();
		// THEN
		// there are 3 entries
		assertListSize(3);
		// correctly ordered
		assertTextOfListChild(0, R.id.weightListItemWeight, "75.0");
		assertTextOfListChild(1, R.id.weightListItemWeight, "80.0");
		assertTextOfListChild(2, R.id.weightListItemWeight, "85.0");
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new WeightListActivity();
	}

	// Private -------------------------------------------------------

	private Weight createWeight(Date date, Double weight) {
		Weight ret = new Weight();
		ret.setDate(date);
		ret.setWeight(weight);
		weightDao.create(ret);
		return ret;
	}

	// Inner classes -------------------------------------------------
}
