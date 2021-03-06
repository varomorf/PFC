package com.gyp.pfc.data.db;

import java.io.IOException;
import java.sql.SQLException;

import com.gyp.pfc.data.domain.biometric.Weight;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.meal.Meal;
import com.gyp.pfc.data.domain.meal.MealName;
import com.gyp.pfc.data.domain.meal.Portion;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Configuration class for the ORMLite configuration
 * 
 * @author Alvaro
 * 
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------
	/**
	 * Which classes to use for the configuration writing
	 */
	private static final Class<?>[] CLASSES = new Class[] { Food.class, Exercise.class, Training.class,
			TrainingExercise.class, MealName.class, Portion.class, Meal.class, TrainingHistoric.class,
			Weight.class };

	/**
	 * This method must be launched with each modification to entity classes.
	 */
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", CLASSES);
	}

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
