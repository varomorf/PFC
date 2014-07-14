/**
 * 
 */
package com.gyp.pfc.data.domain.manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * Manager for operations with {@link Training} entities
 * 
 * @author Alvaro
 * 
 */
public class TrainingManager {

	// Constants -----------------------------------------------------

	private static final String LOG_TAG = TrainingManager.class.getName();

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
	public static TrainingManager it() {
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
	 * Adds an exercise to a training with the specified number of repetitions and duration
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
		addExerciseToTraining(training, exercise, seconds, reps, currentExercisesNum);
	}

	/**
	 * Adds an exercise to a training with the specified number of repetitions and duration
	 * 
	 * @param training
	 *            the training to add the exercise
	 * @param exercise
	 *            the exercise to be added
	 * @param seconds
	 *            the seconds that the exercise must be performed
	 * @param reps
	 *            the the repetitions of the exercise
	 * @param pos
	 *            the position of the new exercise
	 */
	public void addExerciseToTraining(Training training, Exercise exercise, int seconds, int reps, int pos) {
		trainingDao.refresh(training);
		TrainingExercise te = new TrainingExercise();
		te.setTraining(training);
		te.setExercise(exercise);
		te.setSeconds(seconds);
		te.setReps(reps);
		te.setPos(pos);
		trainingExerciseDao.create(te);
		trainingDao.update(training);
	}

	/**
	 * Returns a list with all the {@link Training} entities on DB
	 * 
	 * @return a list with all the {@link Training} entities on DB
	 */
	public List<Training> getAllTrainings() {
		return trainingDao.queryForAll();
	}

	/**
	 * It will import the specified {@link Training}.
	 * 
	 * This means, that it will save this {@link Training} to DB, but if DB already holds a {@link Training} with
	 * the same name as the one passed, it will override said food with the one passed.
	 * 
	 * The {@link Exercise} entities used on the training will not be overwritten, but new ones will also be added
	 * to DB.
	 * 
	 * @param training
	 *            the {@link Training} to be imported
	 * @param exercises
	 *            map of existing exercises
	 */
	public void importTraining(Training training, Map<String, Exercise> exercises) {
		QueryBuilder<Training, Integer> query = trainingDao.queryBuilder();
		// id will be either given by DB or copied from existing food
		training.clearId();
		try {
			query.where().eq("name", training.getName());
			List<Training> dbTrainings = query.query();
			if (!dbTrainings.isEmpty()) {
				Training dbTraining = dbTrainings.get(0);
				// the training will be updated -> clear previous exercises
				trainingExerciseDao.delete(dbTraining.getExercises());
				// get id from previous training and update so new data is loaded on DB
				training.setId(dbTraining.getId());
				trainingDao.update(training);
				createTrainingExercises(training, exercises);
			} else {
				trainingDao.create(training);
				createTrainingExercises(training, exercises);
			}
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error while importing exercise with name " + training.getName(), e);
		}
	}

	/**
	 * Returns the {@link TrainingExercise} of the passed {@link Training} under the specified position
	 * 
	 * @param training
	 *            the {@link Training} from which to get the {@link TrainingExercise}
	 * @param pos
	 *            the position of the {@link TrainingExercise} in the {@link Training}
	 * @return the requested {@link TrainingExercise}
	 */
	public TrainingExercise getTrainingExerciseForPos(Training training, int pos) {
		TrainingExercise output = null;
		QueryBuilder<TrainingExercise, Integer> query = trainingExerciseDao.queryBuilder();
		try {
			query.where().eq("training_id", training.getId()).and().eq("pos", pos);
			List<TrainingExercise> dbTrainingExercises = query.query();
			if (!dbTrainingExercises.isEmpty()) {
				output = dbTrainingExercises.get(0);
			}
		} catch (SQLException e) {
			Log.e(LOG_TAG,
					"Error while getting training exercise with pos " + pos + " for training "
							+ training.getName(), e);
		}
		return output;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Creates the {@link TrainingExercise} entities for the passed {@link Training}. {@link Exercise} entities
	 * will be created if not already present on the passed {@link Map} of exercises
	 * 
	 * @param training
	 *            the {@link Training} for which the {@link TrainingExercise} will be created
	 * @param exercises
	 *            a {@link Map} of {@link Exercise} entities with their name as key
	 */
	private void createTrainingExercises(Training training, Map<String, Exercise> exercises) {
		for (TrainingExercise te : training.getExercises()) {
			// the exercise to be used will be the already present on DB or a new one if necessary
			Exercise exercise = createExerciseIfNecessary(exercises, te.getExercise());
			// add the exercise to the training copying the data from the passed one
			addExerciseToTraining(training, exercise, te.getSeconds(), te.getReps(), te.getPos());
		}
	}

	/**
	 * Creates on DB the passed {@link Exercise} if it's not already present on the passed map of exercises
	 * 
	 * @param exercises
	 *            the map of currently present exercises
	 * @param exercise
	 *            the {@link Exercise} to be created if necessary
	 * @return the exercise that should be used (this will be the created one or the already present on DB)
	 */
	private Exercise createExerciseIfNecessary(Map<String, Exercise> exercises, Exercise exercise) {
		if (!exercises.containsKey(exercise.getName())) {
			// exercise does not exist -> must create it
			try {
				Exercise created = ExerciseManager.it().createExercise(exercise.getName(),
						exercise.getDescription(), exercise.getBurntCalories());
				exercises.put(created.getName(), created);
			} catch (EntityNameException e) {
				// its impossible to be here but just to be sure
				Log.e(LOG_TAG,
						"Error while adding exercise " + exercise.getName() + " while importing a training", e);
			}
		}
		return exercises.get(exercise.getName());
	}

	// Inner classes -------------------------------------------------

}
