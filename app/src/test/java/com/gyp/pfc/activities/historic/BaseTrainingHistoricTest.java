package com.gyp.pfc.activities.historic;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.builder.TrainingHistoricBuilder;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
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

	protected static final String TRAINING_1 = "one";

	protected static final String TRAINING_2 = "two";

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
		exerciseManager = ExerciseManager.it();
		exerciseManager.setExerciseDao(new DatabaseHelper(realActivity).getExerciseDao());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected void prepareTestData() throws EntityNameException, ParseException {
		// one exercise
		Exercise e = exerciseManager.createExercise("one exercise", "one exercise", 100);
		// two trainings
		Training t1 = trainingManager.createTraining(TRAINING_1);
		trainingManager.addExerciseToTraining(t1, e, 3600, 1);
		Training t2 = trainingManager.createTraining(TRAINING_2);
		trainingManager.addExerciseToTraining(t2, e, 60, 10);
		// 1st of January @ 9 and 9:10
		Date first9 = DateUtils.parseDate("01/01/2014 09:00", new String[] { "dd/MM/yyyy HH:mm" });
		Date first10 = DateUtils.parseDate("01/01/2014 09:10", new String[] { "dd/MM/yyyy HH:mm" });
		// 6th of January 2014 @ 12 and 13
		Date sixth12 = DateUtils.parseDate("06/01/2014 12:00", new String[] { "dd/MM/yyyy HH:mm" });
		Date sixth13 = DateUtils.parseDate("06/01/2014 13:00", new String[] { "dd/MM/yyyy HH:mm" });
		// two historic
		TrainingHistoric one = new TrainingHistoricBuilder().id(1).training(t1).start(first9).end(first10)
				.getBuilt();
		trainingHistoricDao.create(one);
		TrainingHistoric two = new TrainingHistoricBuilder().id(2).training(t2).start(sixth12).end(sixth13)
				.getBuilt();
		trainingHistoricDao.create(two);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
