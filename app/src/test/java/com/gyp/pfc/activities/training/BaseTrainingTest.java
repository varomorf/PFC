package com.gyp.pfc.activities.training;

import java.util.List;

import android.content.Intent;

import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
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
		trainingExerciseDao = new DatabaseHelper(realActivity).getTrainingExerciseDao();
		List<Training> trainings = trainingDao.queryForAll();
		trainingDao.delete(trainings);
		List<Exercise> exercises = exerciseDao.queryForAll();
		exerciseDao.delete(exercises);
		ExerciseManager.it().setExerciseDao(exerciseDao);
		TrainingManager.it().setTrainingDao(trainingDao);
		TrainingManager.it().setTrainingExerciseDao(trainingExerciseDao);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected Training createTraining(String name) {
		return createTraining(name, true);
	}

	protected Training createTraining(String name, boolean executable) {
		// prepare new training
		Training training = new Training();
		training.setName(name);
		training.setExecutable(executable);
		trainingDao.create(training);
		return training;
	}

	protected TrainingExercise addExerciseToTraining(Training training, Exercise exercise, int reps, int seconds) {
		// add exercise to training with passed seconds
		TrainingExercise te = new TrainingExercise();
		te.setTraining(training);
		te.setExercise(exercise);
		te.setSeconds(seconds);
		te.setReps(reps);
		// save entities
		trainingExerciseDao.create(te);
		return te;
	}

	protected Training createTraining(String name, int reps, int seconds) {
		return createTraining(name, reps, seconds, true);
	}

	protected Training createTraining(String name, int reps, int seconds, boolean executable) {
		// prepare new training
		Training training = createTraining(name, executable);
		// add exercise to training with passed seconds
		addExerciseToTraining(training, exercise, reps, seconds);
		return training;
	}

	protected void intentPassedWithTraining(int id) {
		Training training = trainingDao.queryForId(id);
		Intent intent = new Intent();
		intent.putExtra(AddTrainingActivity.TRAINING, training);
		activity.setIntent(intent);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
