package com.gyp.pfc.data.db;

import java.io.IOException;
import java.sql.SQLException;

import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.Portion;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;
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
			TrainingExercise.class, MealName.class, Portion.class };

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
