package com.gyp.pfc.data;

import java.io.Serializable;

import android.graphics.Color;

/**
 * Holds the nutritional info of a food.
 * 
 * @author Alvaro
 * 
 */
public class Food implements Serializable {
	// TODO fucking comment everything
	private static final long serialVersionUID = 1L;
	private String name;
	private int calories;
	private int sugars;
	private int fats;
	private int color;
	private int sugarColor;
	private int fatsColor;

	public static final int GREEN = 1;
	public static final int YELLOW = 2;
	public static final int RED = 3;

	public Food() {

	}

	public Food(String name, int calories, int sugars, int fats) {
		this.name = name;
		this.calories = calories;
		this.sugars = sugars;
		this.fats = fats;
	}

	public int sugarCalories() {
		return sugars * 4;
	}

	public int fatsCalories() {
		return fats * 9;
	}

	public int sugarPercentage() {
		return percentage(sugarCalories());
	}

	public int fatsPercentage() {
		return percentage(fatsCalories());
	}

	private int percentage(int value) {
		return (int) ((value * 100.0F) / calories);
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
			if (sugarPercentage > 15) {
				if (sugarPercentage > 50) {
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
			if (fatsPercentage > 15) {
				if (fatsPercentage > 30) {
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

}
