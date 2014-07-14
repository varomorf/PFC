package com.gyp.pfc.testUtils;

import static com.gyp.pfc.sharing.FileSharingName.*;
import static com.xtremelabs.robolectric.Robolectric.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.sharing.file.FileSharingActivityTest;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

/**
 * Generates YAML files for testing purposes
 * 
 * @author Alvaro
 * 
 */
public class YamlGenerator extends FileSharingActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	File target = new File("target");

	int number = 100;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
	}

	@Test
	public void foodGenerator() throws IOException {
		// GIVEN
		// WHEN
		for (int i = 0; i < number; i++) {
			FoodManager.it().createFood("Food " + i, "", 0, 0, 0, 0, 0, 0, 0, 0);
		}
		createActivity();
		// export of foods is requested
		clickOn(activity.findViewById(R.id.fileSharingFoodButton));
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), FOOD.getFileName());
		FileUtils.copyFile(file, new File(target, file.getName()));
	}

	@Test
	public void exerciseGenerator() throws IOException, EntityNameException {
		// GIVEN
		// WHEN
		for (int i = 0; i < number; i++) {
			ExerciseManager.it().createExercise("Exercise " + i, "Do the exercise", 100);
		}
		createActivity();
		// export of foods is requested
		clickOn(activity.findViewById(R.id.fileSharingExerciseButton));
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), EXERCISE.getFileName());
		FileUtils.copyFile(file, new File(target, file.getName()));
	}

	@Test
	public void trainingGenerator() throws IOException, EntityNameException {
		// GIVEN
		// WHEN
		for (int i = 0; i < number; i++) {
			Exercise e1 = ExerciseManager.it().createExercise("Exercise " + i, "", 100);
			Training t1 = TrainingManager.it().createTraining("Training " + i);
			TrainingManager.it().addExerciseToTraining(t1, e1, 10, 1);
			TrainingManager.it().addExerciseToTraining(t1, e1, 20, 2);
			TrainingManager.it().addExerciseToTraining(t1, e1, 30, 3);
		}
		createActivity();
		// export of foods is requested
		clickOn(activity.findViewById(R.id.fileSharingTrainingButton));
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), TRAINING.getFileName());
		FileUtils.copyFile(file, new File(target, file.getName()));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
