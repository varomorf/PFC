package com.gyp.pfc.activities.meal;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Base class for Meal related activities' testing
 * 
 * @author alfergon
 * 
 */
public abstract class BaseMealTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<Meal, Integer> dao;
	protected RuntimeExceptionDao<MealName, Integer> daoNames;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		dao = new DatabaseHelper(realActivity).getMealDao();
		daoNames = new DatabaseHelper(realActivity).getMealNameDao();
	}

	protected MealName createMeal(int id, String name, int order) {
		MealName mealName = new MealName();
		mealName.setId(id);
		mealName.setName(name);
		mealName.setOrder(order);
		daoNames.create(mealName);
		return mealName;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
