/**
 * 
 */
package com.gyp.pfc.data.domain.exercise;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Entity for saving historic data for trainings so the user can know how much
 * calories have been burnt.
 * 
 * @author Alvaro
 * 
 */
public class TrainingHistoric implements Comparable<TrainingHistoric>, Serializable {

	private static final long serialVersionUID = 1L;

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Training training;

	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date start = new Date();

	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date end = new Date();

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Returns the amount of calories burnt in the training of this historic
	 * 
	 * @return the amount of calories burnt in the training of this historic
	 */
	public int getBurntCalories() {
		return training.getBurntCalories();
	}

	@Override
	public int compareTo(TrainingHistoric other) {
		return start.compareTo(other.getStart());
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrainingHistoric) {
			return ((TrainingHistoric) obj).getId() == id;
		}
		return false;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
