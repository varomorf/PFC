/**
 * 
 */
package com.gyp.pfc.data.domain;

/**
 * Defines the methods that an object that can provide nutritional information
 * must have
 * 
 * @author Alvaro
 * 
 */
public interface NutritionalInformationProvider {

	/**
	 * Returns the amount of calories the object provides
	 * 
	 * @return the amount of calories the object provides
	 */
	Double getCalories();

	/**
	 * Returns the amount of carbs the object provides
	 * 
	 * @return the amount of carbs the object provides
	 */
	Double getCarbs();

	/**
	 * Returns the amount of protein the object provides
	 * 
	 * @return the amount of protein the object provides
	 */
	Double getProtein();

	/**
	 * Returns the amount of fats the object provides
	 * 
	 * @return the amount of fats the object provides
	 */
	Double getFats();

}
