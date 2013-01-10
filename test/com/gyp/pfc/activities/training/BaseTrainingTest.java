package com.gyp.pfc.activities.training;

import java.util.List;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public abstract class BaseTrainingTest extends BaseActivityTest {

	// Constants -----------------------------------------------------
	protected static final String TRAINING_NAME = "TRAINING_NAME";
	// Attributes ----------------------------------------------------

	protected RuntimeExceptionDao<Training, Integer> trainingDao;
	protected RuntimeExceptionDao<Exercise, Integer> exerciseDao;
	protected RuntimeExceptionDao<TrainingExercise, Integer> trainingExerciseDao;

	protected Exercise exercise;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	public void before() {
		// init DAOs
		super.before();
		trainingDao = new DatabaseHelper(realActivity).getTrainingDao();
		exerciseDao = new DatabaseHelper(realActivity).getExerciseDao();
		trainingExerciseDao = new DatabaseHelper(realActivity)
				.getTrainingExerciseDao();
		List<Training> trainings = trainingDao.queryForAll();
		trainingDao.delete(trainings);
		List<Exercise> exercises = exerciseDao.queryForAll();
		exerciseDao.delete(exercises);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected Training createTraining(String name, int reps, int seconds) {
		// prepare new training
		Training training = new Training();
		training.setName(name);
		trainingDao.create(training);
		// add exercise to training with passed seconds
		TrainingExercise te = new TrainingExercise();
		te.setTraining(training);
		te.setExercise(exercise);
		te.setSeconds(seconds);
		te.setReps(reps);
		// save entities
		trainingExerciseDao.create(te);
		return training;
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
