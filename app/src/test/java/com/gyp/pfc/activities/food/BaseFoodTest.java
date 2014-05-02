/**
 * 
 */
package com.gyp.pfc.activities.food;

import android.content.Intent;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Base class for Food related activities' testing
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseFoodTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	protected static final String FOOD_NAME = "Food name";
	protected static final String FOOD_BRAND = "Food brand";
	protected static final Double FOOD_CALORIES = 300d;
	protected static final Double FOOD_CARBS = 50d;
	protected static final Double FOOD_SUGAR = 20d;
	protected static final Double FOOD_FIBER = 10d;
	protected static final Double FOOD_FATS = 10d;
	protected static final Double FOOD_SATURATED_FATS = 2d;
	protected static final Double FOOD_PROTEINS = 1d;
	protected static final Double FOOD_SODIUM = 0.1d;

	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<Food, String> dao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		dao = new DatabaseHelper(realActivity).getFoodDao();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected Food createFood() {
		// prepare new training
		Food food = createFoodWithOnlyObligatoryFields();
		food.setBrandName(FOOD_BRAND);
		food.setSugar(FOOD_SUGAR);
		food.setFiber(FOOD_FIBER);
		food.setSaturatedFats(FOOD_SATURATED_FATS);
		food.setSodium(FOOD_SODIUM);
		dao.update(food);
		return food;
	}

	protected Food createFoodWithOnlyObligatoryFields() {
		Food food = new Food();
		food.setName(FOOD_NAME);
		food.setCalories(FOOD_CALORIES);
		food.setProtein(FOOD_PROTEINS);
		food.setCarbs(FOOD_CARBS);
		food.setFats(FOOD_FATS);
		dao.create(food);
		return food;
	}

	protected void intentPassedWithFood(Food food) {
		Intent intent = new Intent();
		intent.putExtra(EditFoodActivity.FOOD_TO_EDIT, food);
		activity.setIntent(intent);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
