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
	private int id;
	@DatabaseField(unique = true, canBeNull = false)
	private String name;
	@DatabaseField
	private String description;

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

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Exercise) {
			return ((Exercise) o).getId() == id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
