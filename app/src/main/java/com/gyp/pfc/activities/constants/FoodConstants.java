/**
 * 
 */
package com.gyp.pfc.activities.constants;

import com.gyp.pfc.data.domain.food.Food;

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

	/**
	 * Key for the return food flag
	 */
	String RETURN_FOOD = "RETURN_FOOD";

}
