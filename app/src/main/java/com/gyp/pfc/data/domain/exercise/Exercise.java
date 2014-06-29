package com.gyp.pfc.data.domain.exercise;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Exercise entity
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class Exercise implements Serializable {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 3506529017983171193L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(unique = true, canBeNull = false)
	private String name;
	@DatabaseField
	private String description = "";
	/** The amount of calories burnt per hour for this exercise */
	@DatabaseField(columnName = "burntcalories")
	private Integer burntCalories = 0;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * ORMLite needs a no-arg constructor
	 */
	public Exercise() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBurntCalories() {
		return burntCalories;
	}

	public void setBurntCalories(Integer burntCalories) {
		this.burntCalories = burntCalories;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if(null == id){
			return false;
		}
		if (o instanceof Exercise) {
			return ((Exercise) o).getId() == id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * Clear the exercise's id
	 */
	public void clearId() {
		id = null;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
