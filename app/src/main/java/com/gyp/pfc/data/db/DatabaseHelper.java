package com.gyp.pfc.data.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * <p>
 * ORMLite database helper used to manage the creation and upgrading of the
 * application's database.
 * </p>
 * <p>
 * This class also usually provides the DAOs used by the other classes
 * </p>
 * 
 * @author afernandezgo
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// Constants -----------------------------------------------------
	private static final String DB_NAME = "pfcDatabase";
	private static final int DB_VERSION = 1;

	// Attributes ----------------------------------------------------
	private RuntimeExceptionDao<Food, String> foodDao;
	private RuntimeExceptionDao<Exercise, Integer> exerciseDao;
	private RuntimeExceptionDao<Training, Integer> trainingDao;
	private RuntimeExceptionDao<TrainingExercise, Integer> trainingExerciseDao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context of the application for which the DB is being made
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION, R.raw.ormlite_config);
	}

	// Public --------------------------------------------------------

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "Creating database");
			// create table for food entity
			TableUtils.createTable(connectionSource, Food.class);
			TableUtils.createTable(connectionSource, Exercise.class);
			TableUtils.createTable(connectionSource, Training.class);
			TableUtils.createTable(connectionSource, TrainingExercise.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new DatabaseException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "Upgrading database");
			// drop the databases
			TableUtils.dropTable(connectionSource, Food.class, true);
			TableUtils.dropTable(connectionSource, Exercise.class, true);
			TableUtils.dropTable(connectionSource, Training.class, true);
			TableUtils.dropTable(connectionSource, TrainingExercise.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new DatabaseException(e);
		}

	}

	/**
	 * Factory method for a {@link RuntimeExceptionDao} for the {@link Food}
	 * entity
	 * 
	 * @return The {@link RuntimeExceptionDao} for the {@link Food} entity
	 */
	public RuntimeExceptionDao<Food, String> getFoodDao() {
		if (null == foodDao) {
			foodDao = getRuntimeExceptionDao(Food.class);
		}
		return foodDao;
	}

	/**
	 * Factory method for a {@link RuntimeExceptionDao} for the {@link Exercise}
	 * entity
	 * 
	 * @return The {@link RuntimeExceptionDao} for the {@link Exercise} entity
	 */
	public RuntimeExceptionDao<Exercise, Integer> getExerciseDao() {
		if (null == exerciseDao) {
			exerciseDao = getRuntimeExceptionDao(Exercise.class);
		}
		return exerciseDao;
	}

	/**
	 * Factory method for a {@link RuntimeExceptionDao} for the {@link Exercise}
	 * entity
	 * 
	 * @return The {@link RuntimeExceptionDao} for the {@link Training} entity
	 */
	public RuntimeExceptionDao<Training, Integer> getTrainingDao() {
		if (null == trainingDao) {
			trainingDao = getRuntimeExceptionDao(Training.class);
		}
		return trainingDao;
	}

	/**
	 * Factory method for a {@link RuntimeExceptionDao} for the
	 * {@link TrainingExercise} entity
	 * 
	 * @return The {@link RuntimeExceptionDao} for the {@link TrainingExercise}
	 *         entity
	 */
	public RuntimeExceptionDao<TrainingExercise, Integer> getTrainingExerciseDao() {
		if (null == trainingExerciseDao) {
			trainingExerciseDao = getRuntimeExceptionDao(TrainingExercise.class);
		}
		return trainingExerciseDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		foodDao = null;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
