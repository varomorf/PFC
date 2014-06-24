package com.gyp.pfc.activities.historic;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Base class for TrainingHistoric related activities' testing
 * 
 * @author alfergon
 * 
 */
public abstract class BaseTrainingHistoricTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<TrainingHistoric, Integer> trainingHistoricDao;
	protected TrainingManager trainingManager;
	protected ExerciseManager exerciseManager;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void before() {
		super.before();
		trainingHistoricDao = new DatabaseHelper(realActivity).getTrainingHistoricDao();
		trainingManager = TrainingManager.getInstance();
		trainingManager.setTrainingDao(new DatabaseHelper(realActivity).getTrainingDao());
		trainingManager.setTrainingExerciseDao(new DatabaseHelper(realActivity).getTrainingExerciseDao());
		exerciseManager = ExerciseManager.getInstance();
		exerciseManager.setExerciseDao(new DatabaseHelper(realActivity).getExerciseDao());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
