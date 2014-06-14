package com.gyp.pfc.activities.meal;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.Portion;
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
	protected RuntimeExceptionDao<Food, Integer> daoFood;
	protected RuntimeExceptionDao<Portion, Integer> daoPortion;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		dao = new DatabaseHelper(realActivity).getMealDao();
		daoNames = new DatabaseHelper(realActivity).getMealNameDao();
		daoFood = new DatabaseHelper(realActivity).getFoodDao();
		daoPortion = new DatabaseHelper(realActivity).getPortionDao();
	}

	protected MealName createMeal(int id, String name, int order) {
		MealName mealName = new MealName();
		mealName.setId(id);
		mealName.setName(name);
		mealName.setOrder(order);
		daoNames.create(mealName);
		return mealName;
	}
	
	protected Portion createPortion(int quantity, double calories, double carbs, double protein, double fats) {
		Food food = new Food();
		food.setName("Food");
		food.setCalories(calories);
		food.setCarbs(carbs);
		food.setProtein(protein);
		food.setFats(fats);
		daoFood.create(food);
		Portion p = new Portion();
		p.setFood(food);
		p.setQuantity(quantity);
		daoPortion.create(p);
		return p;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
