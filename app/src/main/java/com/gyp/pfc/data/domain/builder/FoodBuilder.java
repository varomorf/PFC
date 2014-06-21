/**
 * 
 */
package com.gyp.pfc.data.domain.builder;

import com.gyp.pfc.data.domain.food.Food;

/**
 * Builder for {@link Food} entities
 * 
 * @author Alvaro
 * 
 */
public class FoodBuilder {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * The built food
	 */
	private Food food;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link FoodBuilder} initializing the built food
	 */
	public FoodBuilder() {
		this.food = new Food();
	}

	// Public --------------------------------------------------------

	/**
	 * Sets the food's id
	 * 
	 * @param id
	 *            the id to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder id(int id) {
		food.setId(id);
		return this;
	}

	/**
	 * Sets the food's name
	 * 
	 * @param name
	 *            the name to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder name(String name) {
		food.setName(name);
		return this;
	}

	/**
	 * Sets the food's calories
	 * 
	 * @param calories
	 *            the calories to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder calories(double calories) {
		food.setCalories(calories);
		return this;
	}

	/**
	 * Sets the food's calories
	 * 
	 * @param calories
	 *            the calories to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder calories(String calories) {
		food.setCalories(calories);
		return this;
	}

	/**
	 * Sets the food's sugar
	 * 
	 * @param sugar
	 *            the sugar to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder sugar(Double sugar) {
		food.setSugar(sugar);
		return this;
	}

	/**
	 * Sets the food's sugar
	 * 
	 * @param sugar
	 *            the sugar to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder sugar(String sugar) {
		food.setSugar(sugar);
		return this;
	}

	/**
	 * Sets the food's fats
	 * 
	 * @param fats
	 *            the fats to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder fats(Double fats) {
		food.setFats(fats);
		return this;
	}

	/**
	 * Sets the food's fats
	 * 
	 * @param fats
	 *            the fats to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder fats(String fats) {
		food.setFats(fats);
		return this;
	}

	/**
	 * Sets the food's brandName
	 * 
	 * @param brandName
	 *            the brandName to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder brandName(String brandName) {
		food.setBrandName(brandName);
		return this;
	}

	/**
	 * Sets the food's protein
	 * 
	 * @param protein
	 *            the protein to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder protein(Double protein) {
		food.setProtein(protein);
		return this;
	}

	/**
	 * Sets the food's protein
	 * 
	 * @param protein
	 *            the protein to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder protein(String protein) {
		food.setProtein(protein);
		return this;
	}

	/**
	 * Sets the food's carbs
	 * 
	 * @param carbs
	 *            the carbs to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder carbs(Double carbs) {
		food.setCarbs(carbs);
		return this;
	}

	/**
	 * Sets the food's carbs
	 * 
	 * @param carbs
	 *            the carbs to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder carbs(String carbs) {
		food.setCarbs(carbs);
		return this;
	}

	/**
	 * Sets the food's fiber
	 * 
	 * @param fiber
	 *            the fiber to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder fiber(Double fiber) {
		food.setFiber(fiber);
		return this;
	}

	/**
	 * Sets the food's fiber
	 * 
	 * @param fiber
	 *            the fiber to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder fiber(String fiber) {
		food.setFiber(fiber);
		return this;
	}

	/**
	 * Sets the food's saturatedFats
	 * 
	 * @param saturatedFats
	 *            the saturatedFats to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder saturatedFats(Double saturatedFats) {
		food.setSaturatedFats(saturatedFats);
		return this;
	}

	/**
	 * Sets the food's saturatedFats
	 * 
	 * @param saturatedFats
	 *            the saturatedFats to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder saturatedFats(String saturatedFats) {
		food.setSaturatedFats(saturatedFats);
		return this;
	}

	/**
	 * Sets the food's sodium
	 * 
	 * @param sodium
	 *            the sodium to be set to the built food
	 * @return this {@link PortionBuilder}
	 */
	public FoodBuilder sodium(Double sodium) {
		food.setSodium(sodium);
		return this;
	}

	/**
	 * Returns the built food
	 * 
	 * @return the built food
	 */
	public Food getFood() {
		return food;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
