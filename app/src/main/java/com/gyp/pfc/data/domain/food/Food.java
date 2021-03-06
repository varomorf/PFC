package com.gyp.pfc.data.domain.food;

import java.io.Serializable;

import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.api.NutritionalInformationProvider;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Holds the nutritional info of a food.
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class Food implements Serializable, NutritionalInformationProvider {

	// Constants -----------------------------------------------------

	private static final int CALORIES_PER_FAT_GRAM = 9;
	private static final int CALORIES_PER_SUGAR_GRAM = 4;
	private static final int CALORIES_PER_PROTEIN_GRAM = 4;

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(canBeNull = false, unique = true)
	private String name;
	@DatabaseField(columnName = "brandname")
	private String brandName;
	@DatabaseField(canBeNull = false)
	private Double calories = 0d;
	@DatabaseField(canBeNull = false)
	private Double protein = 0d;
	@DatabaseField(canBeNull = false)
	private Double carbs = 0d;
	@DatabaseField
	private Double sugar = 0d;
	@DatabaseField
	private Double fiber = 0d;
	@DatabaseField(canBeNull = false)
	private Double fats = 0d;
	@DatabaseField(columnName = "saturatedfats")
	private Double saturatedFats = 0d;
	@DatabaseField
	private Double sodium = 0d;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * ORMLite needs a no-arg constructor
	 */
	public Food() {
	}

	// Public --------------------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	/**
	 * Sets the calories trying to parse an int from the passed String. If no int can be parsed, null will be set.
	 * 
	 * @param calories
	 *            the string with the amount of calories
	 */
	public void setCalories(String calories) {
		this.calories = UIUtils.parseDouble(calories);
	}

	public Double getSugar() {
		return sugar;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	/**
	 * Sets the sugar trying to parse a double from the passed String. If no double can be parsed, null will be
	 * set.
	 * 
	 * @param sugar
	 *            the string with the amount of sugar
	 */
	public void setSugar(String sugar) {
		this.sugar = UIUtils.parseDouble(sugar);
	}

	public Double getFats() {
		return fats;
	}

	public void setFats(Double fats) {
		this.fats = fats;
	}

	/**
	 * Sets the fats trying to parse a double from the passed String. If no double can be parsed, null will be set.
	 * 
	 * @param fats
	 *            the string with the amount of fats
	 */
	public void setFats(String fats) {
		this.fats = UIUtils.parseDouble(fats);
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	/**
	 * Sets the protein trying to parse a double from the passed String. If no double can be parsed, null will be
	 * set.
	 * 
	 * @param protein
	 *            the string with the amount of protein
	 */
	public void setProtein(String protein) {
		this.protein = UIUtils.parseDouble(protein);
	}

	public Double getCarbs() {
		return carbs;
	}

	public void setCarbs(Double carbs) {
		this.carbs = carbs;
	}

	/**
	 * Sets the carbs trying to parse a double from the passed String. If no double can be parsed, null will be
	 * set.
	 * 
	 * @param carbs
	 *            the string with the amount of carbs
	 */
	public void setCarbs(String carbs) {
		this.carbs = UIUtils.parseDouble(carbs);
	}

	public Double getFiber() {
		return fiber;
	}

	public void setFiber(Double fiber) {
		this.fiber = fiber;
	}

	/**
	 * Sets the fiber trying to parse a double from the passed String. If no double can be parsed, null will be
	 * set.
	 * 
	 * @param fiber
	 *            the string with the amount of fiber
	 */
	public void setFiber(String fiber) {
		this.fiber = UIUtils.parseDouble(fiber);
	}

	public Double getSaturatedFats() {
		return saturatedFats;
	}

	public void setSaturatedFats(Double saturatedFats) {
		this.saturatedFats = saturatedFats;
	}

	/**
	 * Sets the saturatedFats trying to parse a double from the passed String. If no double can be parsed, null
	 * will be set.
	 * 
	 * @param saturatedFats
	 *            the string with the amount of saturatedFats
	 */
	public void setSaturatedFats(String saturatedFats) {
		this.saturatedFats = UIUtils.parseDouble(saturatedFats);
	}

	public Double getSodium() {
		return sodium;
	}

	public void setSodium(Double sodium) {
		this.sodium = sodium;
	}

	/**
	 * Sets the sodium trying to parse a double from the passed String. If no double can be parsed, null will be
	 * set.
	 * 
	 * @param sodium
	 *            the string with the amount of sodium
	 */
	public void setSodium(String sodium) {
		this.sodium = UIUtils.parseDouble(sodium);
	}

	/**
	 * Calculates the amount of calories from sugar
	 * 
	 * @return the amount of calories from sugar
	 */
	public double getSugarCalories() {
		return sugar * CALORIES_PER_SUGAR_GRAM;
	}

	/**
	 * Calculates the amount of calories from fats
	 * 
	 * @return the amount of calories from fats
	 */
	public double getFatsCalories() {
		return fats * CALORIES_PER_FAT_GRAM;
	}

	/**
	 * Calculates the amount of calories from proteins
	 * 
	 * @return the amount of calories from proteins
	 */
	public double getProteinCalories() {
		return protein * CALORIES_PER_PROTEIN_GRAM;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(brandName).append("-").append(name).append(": ").append(calories).append("Kcal");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Food) {
			return obj.hashCode() == hashCode();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * Clear the food's id
	 */
	public void clearId() {
		id = null;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
