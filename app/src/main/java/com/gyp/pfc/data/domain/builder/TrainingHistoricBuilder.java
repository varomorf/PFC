/**
 * 
 */
package com.gyp.pfc.data.domain.builder;

import java.util.Date;

import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;

/**
 * Builder for the {@link TrainingHistoric} entity
 * 
 * @author Alvaro
 * 
 */
public class TrainingHistoricBuilder {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * The built {@link TrainingHistoric}
	 */
	private TrainingHistoric built;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new builder for a new {@link TrainingHistoric}
	 */
	public TrainingHistoricBuilder() {
		built = new TrainingHistoric();
	}

	// Public --------------------------------------------------------

	/**
	 * Sets the built {@link TrainingHistoric} id
	 * 
	 * @param id
	 *            the new id
	 * @return this {@link TrainingHistoricBuilder}
	 */
	public TrainingHistoricBuilder id(Integer id) {
		built.setId(id);
		return this;
	}

	/**
	 * Sets the built {@link TrainingHistoric} training
	 * 
	 * @param training
	 *            the new training
	 * @return this {@link TrainingHistoricBuilder}
	 */
	public TrainingHistoricBuilder training(Training training) {
		built.setTraining(training);
		return this;
	}

	/**
	 * Sets the built {@link TrainingHistoric} start
	 * 
	 * @param start
	 *            the new start
	 * @return this {@link TrainingHistoricBuilder}
	 */
	public TrainingHistoricBuilder start(Date start) {
		built.setStart(start);
		return this;
	}

	/**
	 * Sets the built {@link TrainingHistoric} end
	 * 
	 * @param end
	 *            the new end
	 * @return this {@link TrainingHistoricBuilder}
	 */
	public TrainingHistoricBuilder end(Date end) {
		built.setEnd(end);
		return this;
	}

	/**
	 * Returns the built {@link TrainingHistoric}
	 * 
	 * @return the built {@link TrainingHistoric}
	 */
	public TrainingHistoric getBuilt() {
		return built;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
