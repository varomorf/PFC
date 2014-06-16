/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.builder.MealNameBuilder;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Manager for operations with {@link MealName
 * } entities
 * 
 * @author Alvaro
 * 
 */
public class MealNameManager {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The DAO to use when creating MealName entities */
	private RuntimeExceptionDao<MealName, Integer> mealNameDao;

	// Static --------------------------------------------------------

	/** Singleton instance of the MealNameManager */
	private static MealNameManager instance;

	/**
	 * Returns the singleton instance of the MealNameManager
	 * 
	 * @return the singleton instance of the MealNameManager
	 */
	public static MealNameManager getInstance() {
		if (null == instance) {
			instance = new MealNameManager();
		}
		return instance;
	}

	// Constructors --------------------------------------------------

	/**
	 * Forbid creation of TrainingManager objects from outside class
	 */
	private MealNameManager() {

	}

	// Public --------------------------------------------------------

	/**
	 * Sets the MealName DAO to be used
	 * 
	 * @param mealNameDao
	 *            the MealName DAO to be used
	 */
	public void setMealNameDao(RuntimeExceptionDao<MealName, Integer> mealNameDao) {
		this.mealNameDao = mealNameDao;
	}

	/**
	 * Creates a new meal name specifying its name and order as long as the
	 * order is not duplicated
	 * 
	 * @param name
	 *            the name of the new MealName
	 * @param order
	 *            the order of the new MealName
	 * @return the new MealName
	 */
	public MealName createMealNameIfOrderNotPresent(String name, int order) {
		MealName mealName = null;
		if (mealNameDao.queryForEq("order", order).size() == 0) {
			mealName = new MealNameBuilder().name(name).order(order).getMealName();
			mealNameDao.create(mealName);
		}
		return mealName;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
