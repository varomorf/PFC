package com.gyp.pfc.activities.meal;

import java.util.Date;
import java.util.List;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.Portion;
import com.gyp.pfc.data.domain.builder.FoodBuilder;
import com.gyp.pfc.data.domain.builder.PortionBuilder;
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

	protected MealName firstName;

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

		List<MealName> mealNames = daoNames.queryForAll();
		daoNames.delete(mealNames);
		firstName = createMeal(1, "First", 1);
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
		Food food = new FoodBuilder().name("Food").calories(calories).carbs(carbs).protein(protein).fats(fats)
				.getFood();
		return createPortion(quantity, food);
	}

	protected Portion createPortion(int quantity, Food food) {
		daoFood.create(food);
		Portion p = new PortionBuilder().food(food).quantity(quantity).getPortion();
		return p;
	}

	protected Meal createMeal(Date date) {
		if (date == null) {
			date = new Date();
		}
		Meal meal = new Meal();
		meal.setDate(date);
		meal.setName(firstName);
		dao.create(meal);
		dao.refresh(meal);
		return meal;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
