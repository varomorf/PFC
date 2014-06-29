/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import java.util.Collections;
import java.util.List;

import com.gyp.pfc.data.domain.biometric.Weight;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Manager for operations with {@link Weight} entities
 * 
 * @author Alvaro
 * 
 */
public class WeightManager {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The DAO to use when creating {@link Weight} entities */
	private RuntimeExceptionDao<Weight, Integer> dao;

	// Static --------------------------------------------------------

	/** Singleton instance of the {@link WeightManager} */
	private static WeightManager instance;

	/**
	 * Returns the singleton instance of the {@link WeightManager}
	 * 
	 * @return the singleton instance of the {@link WeightManager}
	 */
	public static WeightManager it() {
		if (null == instance) {
			instance = new WeightManager();
		}
		return instance;
	}

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Sets the manager's weight dao
	 * 
	 * @param weightDao
	 */
	public void setWeightDao(RuntimeExceptionDao<Weight, Integer> dao) {
		this.dao = dao;
	}

	/**
	 * Returns the last {@link Weight} entered on DB.
	 * 
	 * @return the last {@link Weight} entered on DB (can be <code>null</code>)
	 */
	public Weight getLastWeight() {
		Weight weight = null;
		List<Weight> weights = getSortedWeights();
		if (!weights.isEmpty()) {
			weight = weights.get(0);
		}
		return weight;
	}

	/**
	 * Returns all {@link Weight} entities on DB sorted descending by date
	 * 
	 * @return all {@link Weight} entities on DB sorted descending by date
	 */
	public List<Weight> getSortedWeights() {
		List<Weight> weights = dao.queryForAll();
		Collections.sort(weights, Collections.reverseOrder());
		return weights;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
