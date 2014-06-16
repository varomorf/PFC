/**
 * 
 */
package com.gyp.pfc.activities.food;

import com.gyp.pfc.data.domain.Food;

/**
 * Constants for {@link Food} related activities
 * 
 * @author Alvaro
 * 
 */
public interface FoodConstants {

	/**
	 * Result code for food edition
	 */
	int EDIT_FOOD = 1;
	
	/**
	 * Result code for food selection
	 */
	int SELECT_FOOD = 2;

	/**
	 * Key for the selected food
	 */
	String SELECTED_FOOD = "selectedFood";

}
