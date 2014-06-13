/**
 * 
 */
package com.gyp.pfc.data.domain;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Defines the name of a meal (like breakfast, and so)
 * 
 * @author Alvaro
 * 
 */
@DatabaseTable
public class MealName implements Serializable, Comparable<MealName> {

	// Constants -----------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(canBeNull = false, unique = true)
	private Integer order;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Override
	public int compareTo(MealName o) {
		// compare via meal order
		return order.compareTo(o.order);
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MealName){
			return obj.hashCode() == hashCode();
		}
		return false;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(id).append(" - ").append(name).append("@").append(order).toString();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
