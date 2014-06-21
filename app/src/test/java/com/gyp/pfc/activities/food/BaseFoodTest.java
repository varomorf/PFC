/**
 * 
 */
package com.gyp.pfc.activities.food;

import java.sql.SQLException;

import android.content.Intent;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.food.Food;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

/**
 * Base class for Food related activities' testing
 * 
 * @author Alvaro
 * 
 */
public abstract class BaseFoodTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	protected static final int FOOD_ID = 1;
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

	protected RuntimeExceptionDao<Food, Integer> dao;
	protected Food food;

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
		food.setId(FOOD_ID);
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
	
	protected Intent intentWithFood(Food food){
		Intent intent = new Intent();
		intent.putExtra(EditFoodActivity.SELECTED_FOOD, food);
		return intent;
	}

	protected void intentPassedWithFood(Food food) {
		Intent intent = intentWithFood(food);
		activity.setIntent(intent);
	}
	
	protected void editFoodName(String previousName, String newName) {
		UpdateBuilder<Food, Integer> ub = dao.updateBuilder();
		try {
			ub.updateColumnValue("name", newName);
			ub.where().like("name", previousName);
			ub.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void passFoodToActivity() {
		food = createFood();
		passFoodToActivity(food);
	}

	protected void passFoodToActivity(Food food) {
		intentPassedWithFood(food);
	}

	protected void assertFoodData() {
		assertViewContaining(R.id.foodDetailsName, BaseFoodTest.FOOD_NAME);
		assertViewContaining(R.id.foodDetailsName, BaseFoodTest.FOOD_BRAND);
		assertViewText(R.id.caloriesText, BaseFoodTest.FOOD_CALORIES.toString());
		assertViewText(R.id.proteinsText, BaseFoodTest.FOOD_PROTEINS.toString());
		assertViewText(R.id.carbsText, BaseFoodTest.FOOD_CARBS.toString());
		assertViewText(R.id.sugarText, BaseFoodTest.FOOD_SUGAR.toString());
		assertViewText(R.id.fatsText, BaseFoodTest.FOOD_FATS.toString());
		assertViewText(R.id.saturatedFatsText, BaseFoodTest.FOOD_SATURATED_FATS.toString());
		assertViewText(R.id.fiberText, BaseFoodTest.FOOD_FIBER.toString());
		assertViewText(R.id.sodiumText, new Double(BaseFoodTest.FOOD_SODIUM * 1000).toString());
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
