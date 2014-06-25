package com.gyp.pfc.activities.biometric;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Base class for {@link Weight} related activities' testing
 * 
 * @author alfergon
 * 
 */
public abstract class BaseWeightActivityTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<Weight, Integer> weightDao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		weightDao = new DatabaseHelper(realActivity).getWeightDao();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
