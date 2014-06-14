/**
 * 
 */
package com.gyp.pfc.data.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.gyp.pfc.data.domain.nulls.NullMealName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 * Defines a meal for a certain day and moment (breakfast, dinner, etc ...)
 * 
 * @author Alvaro
 * 
 */
public class Meal implements Serializable {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date date = new Date();

	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private MealName name = NullMealName.NULL_MEAL_NAME;

	@ForeignCollectionField(eager = true)
	private Collection<Portion> portions = new ArrayList<Portion>();

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Returns the sum of the calories of all the food portions in this meal
	 * 
	 * @return the calories of this meal
	 */
	public Integer getMealCalories() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getFood().getCalories();
		}
		return total.intValue();
	}

	/**
	 * Returns the sum of the carbs of all the food portions in this meal
	 * 
	 * @return the carbs of this meal
	 */
	public Integer getMealCarbs() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getFood().getCarbs();
		}
		return total.intValue();
	}

	/**
	 * Returns the sum of the protein of all the food portions in this meal
	 * 
	 * @return the protein of this meal
	 */
	public Integer getMealProtein() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getFood().getProtein();
		}
		return total.intValue();
	}

	/**
	 * Returns the sum of the fats of all the food portions in this meal
	 * 
	 * @return the fats of this meal
	 */
	public Integer getMealFats() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getFood().getFats();
		}
		return total.intValue();
	}

	@Override
	public String toString() {
		String formattedDate = DateFormatUtils.format(date, "dd/MM/yyyy");
		return new StringBuilder().append(id).append(" - ").append(name.getName()).append("@")
				.append(formattedDate).append(" with ").append(portions.size()).append(" portions").toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MealName getName() {
		return name;
	}

	public void setName(MealName name) {
		this.name = name != null ? name : NullMealName.NULL_MEAL_NAME;
	}

	public Collection<Portion> getPortions() {
		return portions;
	}

	/**
	 * Sets the portions assuring that the collection will never be null
	 * 
	 * @param portions
	 *            the new portions
	 */
	public void setPortions(Collection<Portion> portions) {
		this.portions = portions != null ? portions : new ArrayList<Portion>();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
