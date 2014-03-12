package com.gyp.pfc.data.domain;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Holds the nutritional info of a food.
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class Food implements Serializable {

	private static final int CALORIES_PER_FAT_GRAM = 9;
	private static final int CALORIES_PER_SUGAR_GRAM = 4;
	private static final int CALORIES_PER_PROTEIN_GRAM = 4;

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(columnName = "brandname")
	private String brandName;
	@DatabaseField(canBeNull = false)
	private Double calories;
	@DatabaseField(canBeNull = false)
	private Double protein;
	@DatabaseField(canBeNull = false)
	private Double carbs;
	@DatabaseField
	private Double sugar;
	@DatabaseField
	private Double fiber;
	@DatabaseField(canBeNull = false)
	private Double fats;
	@DatabaseField(columnName = "saturatedfats")
	private Double saturatedFats;
	@DatabaseField
	private Double sodium;

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

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	/**
	 * Sets the calories trying to parse an int from the passed String. If no
	 * int can be parsed, null will be set.
	 * 
	 * @param calories
	 *            the string with the amount of calories
	 */
	public void setCalories(String calories) {
		try {
			this.calories = Double.parseDouble(calories);
		} catch (Exception e) {
			this.calories = null;
		}
	}

	public Double getSugar() {
		return sugar;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	/**
	 * Sets the sugar trying to parse a double from the passed String. If no
	 * double can be parsed, null will be set.
	 * 
	 * @param sugar
	 *            the string with the amount of sugar
	 */
	public void setSugar(String sugar) {
		try {
			this.sugar = Double.parseDouble(sugar);
		} catch (Exception e) {
			this.sugar = null;
		}
	}

	public Double getFats() {
		return fats;
	}

	public void setFats(Double fats) {
		this.fats = fats;
	}

	/**
	 * Sets the fats trying to parse a double from the passed String. If no
	 * double can be parsed, null will be set.
	 * 
	 * @param fats
	 *            the string with the amount of fats
	 */
	public void setFats(String fats) {
		try {
			this.fats = Double.parseDouble(fats);
		} catch (Exception e) {
			this.fats = null;
		}
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
	 * Sets the protein trying to parse a double from the passed String. If no
	 * double can be parsed, null will be set.
	 * 
	 * @param protein
	 *            the string with the amount of protein
	 */
	public void setProtein(String protein) {
		try {
			this.protein = Double.parseDouble(protein);
		} catch (Exception e) {
			this.protein = null;
		}
	}

	public Double getCarbs() {
		return carbs;
	}

	public void setCarbs(Double carbs) {
		this.carbs = carbs;
	}

	/**
	 * Sets the carbs trying to parse a double from the passed String. If no
	 * double can be parsed, null will be set.
	 * 
	 * @param carbs
	 *            the string with the amount of carbs
	 */
	public void setCarbs(String carbs) {
		try {
			this.carbs = Double.parseDouble(carbs);
		} catch (Exception e) {
			this.carbs = null;
		}
	}

	public Double getFiber() {
		return fiber;
	}

	public void setFiber(Double fiber) {
		this.fiber = fiber;
	}

	/**
	 * Sets the fiber trying to parse a double from the passed String. If no
	 * double can be parsed, null will be set.
	 * 
	 * @param fiber
	 *            the string with the amount of fiber
	 */
	public void setFiber(String fiber) {
		try {
			this.fiber = Double.parseDouble(fiber);
		} catch (Exception e) {
			this.fiber = null;
		}
	}

	public Double getSaturatedFats() {
		return saturatedFats;
	}

	public void setSaturatedFats(Double saturatedFats) {
		this.saturatedFats = saturatedFats;
	}

	/**
	 * Sets the saturatedFats trying to parse a double from the passed String.
	 * If no double can be parsed, null will be set.
	 * 
	 * @param saturatedFats
	 *            the string with the amount of saturatedFats
	 */
	public void setSaturatedFats(String saturatedFats) {
		try {
			this.saturatedFats = Double.parseDouble(saturatedFats);
		} catch (Exception e) {
			this.saturatedFats = null;
		}
	}

	public Double getSodium() {
		return sodium;
	}

	public void setSodium(Double sodium) {
		this.sodium = sodium;
	}

	/**
	 * Sets the sodium trying to parse a double from the passed String. If no
	 * double can be parsed, null will be set.
	 * 
	 * @param sodium
	 *            the string with the amount of sodium
	 */
	public void setSodium(String sodium) {
		try {
			this.sodium = Double.parseDouble(sodium);
		} catch (Exception e) {
			this.sodium = null;
		}
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
		String ret = "";
		ret += calories + "calories\n";
		ret += sugar + "sugars\n";
		ret += fats + "fats\n";
		return ret;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
