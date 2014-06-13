/**
 * 
 */
package com.gyp.pfc.data.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.gyp.pfc.data.domain.nulls.NullMealName;
import com.j256.ormlite.dao.ForeignCollection;
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
	private MealName name = new NullMealName();

	@ForeignCollectionField(eager = true)
	private ForeignCollection<Portion> portions;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public String toString() {
		String formattedDate = DateFormatUtils.format(date, "dd/MM/yyyy");
		int size = portions != null ? portions.size() : 0;
		return new StringBuilder().append(id).append(" - ").append(name.getName()).append("@")
				.append(formattedDate).append(" with ").append(size).append(" portions").toString();
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
		this.name = name;
	}

	public ForeignCollection<Portion> getPortions() {
		return portions;
	}

	public void setPortions(ForeignCollection<Portion> portions) {
		this.portions = portions;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
