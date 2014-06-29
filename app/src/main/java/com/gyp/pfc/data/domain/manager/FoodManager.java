/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gyp.pfc.data.domain.food.Food;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * Manager for operations with {@link Food} entities
 * 
 * @author Alvaro
 * 
 */
public class FoodManager {

	// Constants -----------------------------------------------------

	private static final String LOG_TAG = FoodManager.class.getName();

	// Attributes ----------------------------------------------------

	/** The DAO to use when creating Food entities */
	private RuntimeExceptionDao<Food, Integer> foodDAO;

	// Static --------------------------------------------------------

	/** Singleton instance of the FoodManager */
	private static FoodManager instance;

	/**
	 * Returns the singleton instance of the FoodManager
	 * 
	 * @return the singleton instance of the FoodManager
	 */
	public static FoodManager it() {
		if (null == instance) {
			instance = new FoodManager();
		}
		return instance;
	}

	// Constructors --------------------------------------------------

	/**
	 * Forbid creation of FoodManager objects from outside class
	 */
	private FoodManager() {

	}

	// Public --------------------------------------------------------

	/**
	 * Sets the food DAO to be used
	 * 
	 * @param foodDAO
	 *            the food DAO to be used
	 */
	public void setFoodDao(RuntimeExceptionDao<Food, Integer> foodDAO) {
		this.foodDAO = foodDAO;
	}

	/**
	 * Creates a new food specifying its name, calories, grams of proteins, grams of carbs and grams of fats
	 * 
	 * @param name
	 *            the name of the food
	 * @param calories
	 *            the calories of the food
	 * @param protein
	 *            the grams of protein of the food
	 * @param carbs
	 *            the grams of carbs of the food
	 * @param fat
	 *            the grams of fat of the food
	 * @return the created food
	 */
	public Food createFood(String name, double calories, double protein, double carbs, double fat) {
		return createFood(name, "", calories, protein, carbs, 0, 0, fat, 0, 0);
	}

	/**
	 * Creates a new food specifying its name, brand name, calories, grams of protein, grams of carbs, grams of
	 * sugar, grams of fiber, grams of fats, grams of saturated fats and grams of sodium
	 * 
	 * @param name
	 *            the name of the food
	 * @param brandName
	 *            the name of the brand of the food
	 * @param calories
	 *            the amount of calories
	 * @param protein
	 *            the grams of protein
	 * @param carbs
	 *            the grams of carbs
	 * @param sugar
	 *            the grams of sugar
	 * @param fiber
	 *            the grams of fiber
	 * @param fat
	 *            the grams of fat of the food
	 * @param saturatedFat
	 *            the grams of saturated fats
	 * @param sodium
	 *            the grams of sodium
	 * @return the created food
	 */
	public Food createFood(String name, String brandName, double calories, double protein, double carbs,
			double sugar, double fiber, double fat, double saturatedFat, double sodium) {
		Food food = new Food();
		food.setName(name);
		food.setBrandName(brandName);
		food.setCalories(calories);
		food.setProtein(protein);
		food.setCarbs(carbs);
		food.setSugar(sugar);
		food.setFiber(fiber);
		food.setFats(fat);
		food.setSaturatedFats(saturatedFat);
		food.setSodium(sodium);
		foodDAO.create(food);
		return food;
	}

	/**
	 * Returns a list with all the {@link Food} entities on DB
	 * 
	 * @return a list with all the {@link Food} entities on DB
	 */
	public List<Food> getAllFoods() {
		return foodDAO.queryForAll();
	}

	/**
	 * It will import the specified {@link Food}.
	 * 
	 * This means, that it will save this {@link Food} to DB, but if DB already holds a {@link Food} with the same
	 * name as the one passed, it will override said food with the one passed.
	 * 
	 * @param food
	 *            the {@link Food} to be imported
	 */
	public void importFood(Food food) {
		QueryBuilder<Food, Integer> query = foodDAO.queryBuilder();
		// id will be either given by DB or copied from existing food
		food.clearId();
		try {
			query.where().eq("name", food.getName());
			List<Food> dbFoods = query.query();
			if (!dbFoods.isEmpty()) {
				Food dbFood = dbFoods.get(0);
				food.setId(dbFood.getId());
				foodDAO.update(food);
			} else {
				foodDAO.create(food);
			}
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error while importing food with name " + food.getName(), e);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
