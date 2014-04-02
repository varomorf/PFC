/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import com.gyp.pfc.data.domain.Training;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Manager for operations with {@link Training} entities
 * 
 * @author Alvaro
 * 
 */
public class TrainingManager {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The DAO to use when creating Training entities */
	private RuntimeExceptionDao<Training, Integer> trainingDao;

	// Static --------------------------------------------------------

	/** Singleton instance of the TrainingManager */
	private static TrainingManager instance;

	/**
	 * Returns the singleton instance of the TrainingManager
	 * 
	 * @return the singleton instance of the TrainingManager
	 */
	public static TrainingManager getInstance() {
		if (null == instance) {
			instance = new TrainingManager();
		}
		return instance;
	}

	// Constructors --------------------------------------------------

	/**
	 * Forbid creation of TrainingManager objects from outside class
	 */
	private TrainingManager() {

	}

	// Public --------------------------------------------------------

	/**
	 * Sets the training DAO to be used
	 * 
	 * @param trainingDao
	 *            the training DAO to be used
	 */
	public void setTrainingDao(
			RuntimeExceptionDao<Training, Integer> trainingDao) {
		this.trainingDao = trainingDao;
	}

	/**
	 * Creates a new training specifying its name
	 * 
	 * @param name
	 *            the name of the new training
	 * @return the new training
	 */
	public Training createTraining(String name) {
		Training training = new Training();
		training.setName(name);
		trainingDao.create(training);
		return training;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
