/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
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

	/** The DAO to use when creating TrainingExercise entities */
	private RuntimeExceptionDao<TrainingExercise, Integer> trainingExerciseDao;

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
	public void setTrainingDao(RuntimeExceptionDao<Training, Integer> trainingDao) {
		this.trainingDao = trainingDao;
	}

	/**
	 * Sets the TrainingExercise DAO to be used
	 * 
	 * @param trainingExerciseDao
	 *            the trainingExerciseDao DAO to be used
	 */
	public void setTrainingExerciseDao(RuntimeExceptionDao<TrainingExercise, Integer> trainingExerciseDao) {
		this.trainingExerciseDao = trainingExerciseDao;
	}

	/**
	 * Creates a new training specifying its name
	 * 
	 * @param name
	 *            the name of the new training
	 * @return the new training
	 */
	public Training createTraining(String name) {
		return createTraining(name, true);
	}

	/**
	 * Creates a new training specifying its name
	 * 
	 * @param name
	 *            the name of the new training
	 * @param executable
	 *            whether the training is executable or not
	 * @return the new training
	 */
	public Training createTraining(String name, boolean executable) {
		Training training = new Training();
		training.setName(name);
		training.setExecutable(executable);
		trainingDao.create(training);
		return training;
	}

	/**
	 * Adds an exercise to a training with the specified number of repetitions
	 * and duration
	 * 
	 * @param training
	 *            the training to add the exercise
	 * @param exercise
	 *            the exercise to be added
	 * @param seconds
	 *            the seconds that the exercise must be performed
	 * @param reps
	 *            the the repetitions of the exercise
	 */
	public void addExerciseToTraining(Training training, Exercise exercise, int seconds, int reps) {
		trainingDao.refresh(training);
		int currentExercisesNum = training.getExercises().size();
		TrainingExercise te = new TrainingExercise();
		te.setTraining(training);
		te.setExercise(exercise);
		te.setSeconds(seconds);
		te.setReps(reps);
		te.setPos(currentExercisesNum);
		trainingExerciseDao.create(te);
		trainingDao.update(training);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
