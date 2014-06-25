package com.gyp.pfc.data.domain.biometric;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Entity for entering the weight for a certain day.
 * 
 * The saved date will be truncated to the day (so 01/01/2014 @ 9:00 will become
 * 01/01/2014 @ 00:00)
 * 
 * @author alfergon
 * 
 */
@DatabaseTable
public class Weight implements Serializable, Comparable<Weight> {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(dataType = DataType.DATE_STRING, unique = true)
	private Date date = new Date();

	@DatabaseField(canBeNull = false)
	private Double weight = 0d;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date != null ? date : new Date();
		this.date = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(Double weight) {
		if (weight == null) {
			weight = 0d;
		}
		this.weight = weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Weight) {
			return hashCode() == obj.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public int compareTo(Weight o) {
		if (o == null) {
			return 1;
		}
		return date.compareTo(o.getDate());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
