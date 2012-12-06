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
	private static final float HUNDRED = 100.0f;
	private static final int RED_MARGIN_FOR_FATS = 30;
	private static final int YELLOW_MARGIN_FOR_FATS = 10;
	private static final int RED_MARGIN_FOR_SUGAR = 50;
	private static final int YELLOW_MARGIN_FOR_SUGAR = 15;
	private static final int CALORIES_PER_FAT_GRAM = 9;
	private static final int CALORIES_PER_SUGAR_GRAM = 4;
	// TODO fucking comment everything
	// Constants -----------------------------------------------------
	public static final int GREEN = 1;
	public static final int YELLOW = 2;
	public static final int RED = 3;

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------
	@DatabaseField(id = true)
	private String name;
	@DatabaseField
	private int calories;
	@DatabaseField
	private int sugars;
	@DatabaseField
	private int fats;
	private int color;
	private int sugarColor;
	private int fatsColor;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * ORMLite needs a no-arg constructor
	 */
	public Food() {
	}

	// Public --------------------------------------------------------

	public Food(String name, int calories, int sugars, int fats) {
		this.name = name;
		this.calories = calories;
		this.sugars = sugars;
		this.fats = fats;
	}

	public int sugarCalories() {
		return sugars * CALORIES_PER_SUGAR_GRAM;
	}

	public int fatsCalories() {
		return fats * CALORIES_PER_FAT_GRAM;
	}

	public int sugarPercentage() {
		return percentage(sugarCalories());
	}

	public int fatsPercentage() {
		return percentage(fatsCalories());
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

	public int getColor() {
		if (sugarColor == 0) {
			getSugarColor();
		}
		if (fatsColor == 0) {
			getFatsColor();
		}
		if (color == 0) {
			if (sugarColor == Color.GREEN && fatsColor == Color.GREEN) {
				color = Color.GREEN;
			} else if (sugarColor == Color.RED || fatsColor == Color.RED) {
				color = Color.RED;
			} else {
				color = Color.YELLOW;
			}
		}
		return color;
	}

	public int getSugarColor() {
		if (sugarColor == 0) {
			int sugarPercentage = sugarPercentage();
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
		return sugarColor;
	}

	public int getFatsColor() {
		if (fatsColor == 0) {
			int fatsPercentage = fatsPercentage();
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
		return fatsColor;
	}

	public boolean isEmpty() {
		return (calories == 0 && sugars == 0 && fats == 0);
	}

	@Override
	public String toString() {
		// for list view
		return name;
	}

	public String fullToString() {
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
		return (int) ((value * HUNDRED) / calories);
	}

	// Inner classes -------------------------------------------------

}
