package com.gyp.pfc.data.domain;

import java.io.Serializable;

import android.graphics.Color;

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
	private static final int RED_MARGIN_FOR_FATS = 30;
	private static final int YELLOW_MARGIN_FOR_FATS = 10;
	private static final int RED_MARGIN_FOR_SUGAR = 50;
	private static final int YELLOW_MARGIN_FOR_SUGAR = 15;
	private static final int CALORIES_PER_FAT_GRAM = 9;
	private static final int CALORIES_PER_SUGAR_GRAM = 4;
	// Constants -----------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int calories;
	@DatabaseField
	private int sugars;
	@DatabaseField
	private int fats;
	@DatabaseField
	private int color;
	@DatabaseField
	private int sugarColor;
	@DatabaseField
	private int fatsColor;

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

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getSugars() {
		return sugars;
	}

	public void setSugars(int sugars) {
		this.sugars = sugars;
	}

	public int getFats() {
		return fats;
	}

	public void setFats(int fats) {
		this.fats = fats;
	}

	/**
	 * Calculates the amount of calories from sugar
	 * 
	 * @return The amount of calories from sugar
	 */
	public int getSugarCalories() {
		return sugars * CALORIES_PER_SUGAR_GRAM;
	}

	/**
	 * Calculates the amount of calories from fats
	 * 
	 * @return The amount of calories from fats
	 */
	public int getFatsCalories() {
		return fats * CALORIES_PER_FAT_GRAM;
	}

	/**
	 * Calculates the percentage of calories from sugar
	 * 
	 * @return the percentage of calories from sugar
	 */
	public int getSugarPercentage() {
		return percentage(getSugarCalories());
	}

	/**
	 * Calculates the percentage of calories from fats
	 * 
	 * @return the percentage of calories from fats
	 */
	public int getFatsPercentage() {
		return percentage(getFatsCalories());
	}

	/**
	 * Returns the color of the food calculating it if necessary
	 * 
	 * @return the color of the food
	 */
	public int getColor() {
		if (color == 0) {
			calculateColor();
		}
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Returns the color of the sugar calculating it if necessary
	 * 
	 * @return the color of the sugar
	 */
	public int getSugarColor() {
		if (sugarColor == 0) {
			calculateSugarColor();
		}
		return sugarColor;
	}

	public void setSugarColor(int sugarColor) {
		this.sugarColor = sugarColor;
	}

	/**
	 * Returns the color of the fats calculating it if necessary
	 * 
	 * @return the color of the fats
	 */
	public int getFatsColor() {
		if (fatsColor == 0) {
			calculateFatsColor();
		}
		return fatsColor;
	}

	public void setFatsColor(int fatsColor) {
		this.fatsColor = fatsColor;
	}

	/**
	 * Returns whether the food has no field set
	 * 
	 * @return True if calories, sugars and fats are 0. False otherwise
	 */
	public boolean isEmpty() {
		return (calories | sugars | fats) == 0;
	}

	@Override
	public String toString() {
		String ret = "";
		ret += calories + "calories\n";
		ret += sugars + "sugars\n";
		ret += fats + "fats\n";
		ret += color + "color\n";
		return ret;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private int percentage(int value) {
		return (int) ((value * 100) / calories);
	}

	private void calculateSugarColor() {
		int sugarPercentage = getSugarPercentage();
		if (sugarPercentage > YELLOW_MARGIN_FOR_SUGAR) {
			if (sugarPercentage > RED_MARGIN_FOR_SUGAR) {
				sugarColor = Color.RED;
			} else {
				sugarColor = Color.YELLOW;
			}
		} else {
			sugarColor = Color.GREEN;
		}
	}

	private void calculateFatsColor() {
		int fatsPercentage = getFatsPercentage();
		if (fatsPercentage > YELLOW_MARGIN_FOR_FATS) {
			if (fatsPercentage > RED_MARGIN_FOR_FATS) {
				fatsColor = Color.RED;
			} else {
				fatsColor = Color.YELLOW;
			}
		} else {
			fatsColor = Color.GREEN;
		}
	}

	private void calculateColor() {
		if (sugarColor == 0) {
			calculateSugarColor();
		}
		if (fatsColor == 0) {
			calculateFatsColor();
		}
		if (sugarColor == Color.GREEN && fatsColor == Color.GREEN) {
			color = Color.GREEN;
		} else if (sugarColor == Color.RED || fatsColor == Color.RED) {
			color = Color.RED;
		} else {
			color = Color.YELLOW;
		}
	}

	// Inner classes -------------------------------------------------

}
