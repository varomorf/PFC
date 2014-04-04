/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Manager for operations with {@link Food} entities
 * 
 * @author Alvaro
 * 
 */
public class FoodManager {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The DAO to use when creating Food entities */
	private RuntimeExceptionDao<Food, String> foodDAO;

	// Static --------------------------------------------------------

	/** Singleton instance of the FoodManager */
	private static FoodManager instance;

	/**
	 * Returns the singleton instance of the FoodManager
	 * 
	 * @return the singleton instance of the FoodManager
	 */
	public static FoodManager getInstance() {
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
	public void setFoodDao(RuntimeExceptionDao<Food, String> foodDAO) {
		this.foodDAO = foodDAO;
	}

	/**
	 * Creates a new food specifying its name, calories, grams of proteins,
	 * grams of carbs and grams of fats
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
	public Food createFood(String name, double calories, double protein,
			double carbs, double fat) {
		return createFood(name, "", calories, protein, carbs, 0, 0, fat, 0, 0);
	}

	/**
	 * Creates a new food specifying its name, brand name, calories, grams of
	 * protein, grams of carbs, grams of sugar, grams of fiber, grams of fats,
	 * grams of saturated fats and grams of sodium
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
	public Food createFood(String name, String brandName, double calories,
			double protein, double carbs, double sugar, double fiber,
			double fat, double saturatedFat, double sodium) {
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
	 * Creates a new food specifying its name, brand name, calories, grams of
	 * protein, grams of carbs, grams of sugar, grams of fiber, grams of fats,
	 * grams of saturated fats and grams of sodium
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
	public Food createFood(String name, String brandName, String calories,
			String protein, String carbs, String sugar, String fiber,
			String fat, String saturatedFat, String sodium) {
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
