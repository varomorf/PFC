/**
 * 
 */
package com.gyp.pfc.data.domain.meal;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.gyp.pfc.data.domain.api.NullForeignCollection;
import com.gyp.pfc.data.domain.api.NutritionalInformationProvider;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Defines a meal for a certain day and moment (breakfast, dinner, etc ...)
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class Meal implements Serializable, NutritionalInformationProvider {

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
	private ForeignCollection<Portion> portions = new NullForeignCollection<Portion, Integer>();

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public Double getCalories() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getCalories();
		}
		return new Double(Math.round(total));
	}

	@Override
	public Double getCarbs() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getCarbs();
		}
		return new Double(Math.round(total));
	}

	@Override
	public Double getProtein() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getProtein();
		}
		return new Double(Math.round(total));
	}

	@Override
	public Double getFats() {
		Double total = 0d;
		for (Portion portion : portions) {
			total += portion.getFats();
		}
		return new Double(Math.round(total));
	}

	/**
	 * Adds the passed portion to the portions list
	 * 
	 * @param portion
	 *            the portion to be added
	 */
	public void addPortion(Portion portion) {
		portions.add(portion);
		portion.setMeal(this);
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

	/**
	 * Sets the date stripping it from its time
	 * 
	 * @param date
	 *            the date to be set
	 */
	public void setDate(Date date) {
		this.date = date != null ? date : new Date();
		this.date = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	public MealName getName() {
		return name;
	}

	public void setName(MealName name) {
		this.name = name != null ? name : NullMealName.NULL_MEAL_NAME;
	}

	public ForeignCollection<Portion> getPortions() {
		return portions;
	}

	/**
	 * Sets the portions assuring that the collection will never be null
	 * 
	 * @param portions
	 *            the new portions
	 */
	public void setPortions(ForeignCollection<Portion> portions) {
		this.portions = portions != null ? portions : new NullForeignCollection<Portion, Integer>();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
