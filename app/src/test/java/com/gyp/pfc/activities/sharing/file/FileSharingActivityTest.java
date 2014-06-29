package com.gyp.pfc.activities.sharing.file;

import static com.gyp.pfc.sharing.FileSharingName.*;
import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Yaml;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.gyp.pfc.sharing.FileSharingName;
import com.gyp.pfc.sharing.TrainingConstructor;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

/**
 * @author alfergon
 * 
 */
@RunWith(CustomTestRunner.class)
public class FileSharingActivityTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
		DatabaseHelper dbh = new DatabaseHelper(realActivity);
		FoodManager.it().setFoodDao(dbh.getFoodDao());
		ExerciseManager.getInstance().setExerciseDao(dbh.getExerciseDao());
		TrainingManager.getInstance().setTrainingExerciseDao(dbh.getTrainingExerciseDao());
		TrainingManager.getInstance().setTrainingDao(dbh.getTrainingDao());
	}

	@Test
	public void shouldExportFoodEntities() throws FileNotFoundException {
		// GIVEN
		// two foods on DB
		FoodManager.it().createFood("Arroz", 150d, 50d, 100d, 5d);
		FoodManager.it().createFood("Pan", 250d, 10d, 150d, 10d);
		// WHEN
		// activity is created
		createActivity();
		// export of foods is requested
		clickOn(activity.findViewById(R.id.fileSharingFoodButton));
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), FOOD.getFileName());
		// print file path for checking it
		System.out.println(file.getPath());
		// the file must exist
		assertTrue("The file for the export does not exist", file.isFile());
		// file should have expected data
		List<Food> foods = new ArrayList<Food>();
		for (Object o : new Yaml().loadAll(new FileInputStream(file))) {
			foods.add((Food) o);
		}
		assertEquals("There should have been data for two foods", 2, foods.size());
		// assert first food
		Food food = foods.get(0);
		assertEquals("Arroz", food.getName());
		assertEquals(150d, food.getCalories(), 0);
		assertEquals(100d, food.getCarbs(), 0);
		assertEquals(50d, food.getProtein(), 0);
		assertEquals(5d, food.getFats(), 0);
		// assert second food
		Food food2 = foods.get(1);
		assertEquals("Pan", food2.getName());
		assertEquals(250d, food2.getCalories(), 0);
		assertEquals(150d, food2.getCarbs(), 0);
		assertEquals(10d, food2.getProtein(), 0);
		assertEquals(10d, food2.getFats(), 0);
	}

	@Test
	public void shouldExportExecutableTrainingEntities() throws FileNotFoundException, EntityNameException {
		// GIVEN
		// two executable trainings on DB with two different exercises
		Exercise e1 = ExerciseManager.getInstance().createExercise("One", "", 100);
		Exercise e2 = ExerciseManager.getInstance().createExercise("Two", "", 200);
		Training t1 = TrainingManager.getInstance().createTraining("One");
		Training t2 = TrainingManager.getInstance().createTraining("Two");
		TrainingManager.getInstance().addExerciseToTraining(t1, e1, 10, 1);
		TrainingManager.getInstance().addExerciseToTraining(t2, e1, 20, 2);
		TrainingManager.getInstance().addExerciseToTraining(t2, e2, 30, 3);
		// one not executable training
		TrainingManager.getInstance().createTraining("Three", false);
		// WHEN
		// activity is created
		createActivity();
		// export of trainings is requested
		clickOn(activity.findViewById(R.id.fileSharingTrainingButton));
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), TRAINING.getFileName());
		// print file path for checking it
		System.out.println(file.getPath());
		// the file must exist
		assertTrue("The file for the export does not exist", file.isFile());
		// file should have expected data
		List<Training> trainings = new ArrayList<Training>();
		for (Object o : new Yaml(new TrainingConstructor()).loadAll(new FileInputStream(file))) {
			trainings.add((Training) o);
		}
		assertEquals("There should have been data for two foods", 2, trainings.size());
		// assert first Exercise
		Training training1 = trainings.get(0);
		assertEquals("One", training1.getName());
		assertTrue(training1.isExecutable());
		assertEquals(1, training1.getExercises().size());
		TrainingExercise te1 = (TrainingExercise) training1.getExercises().toArray()[0];
		assertEquals(e1.getName(), te1.getExercise().getName());
		assertEquals(10, te1.getSeconds());
		assertEquals(1, te1.getReps());
		// assert second Exercise
		Training training2 = trainings.get(1);
		assertEquals("Two", training2.getName());
		assertEquals(2, training2.getExercises().size());
	}

	@Test
	public void shouldExportExerciseEntities() throws FileNotFoundException, EntityNameException {
		// GIVEN
		// two exercises on DB
		String description = "With short description";
		String longDescription = "With long description With long descriptionWith long descriptionWith long descriptionWith long descriptionWith long descriptionWith long descriptionWith long descriptionWith long descriptionWith long descriptionWith long description";
		ExerciseManager.getInstance().createExercise("One", "With short description", 100);
		ExerciseManager.getInstance().createExercise("Two", longDescription, 200);
		// WHEN
		// activity is created
		createActivity();
		// export of exercises is requested
		clickOn(activity.findViewById(R.id.fileSharingExerciseButton));
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), EXERCISE.getFileName());
		// print file path for checking it
		System.out.println(file.getPath());
		// the file must exist
		assertTrue("The file for the export does not exist", file.isFile());
		// file should have expected data
		List<Exercise> exercises = new ArrayList<Exercise>();
		for (Object o : new Yaml().loadAll(new FileInputStream(file))) {
			exercises.add((Exercise) o);
		}
		assertEquals("There should have been data for two foods", 2, exercises.size());
		// assert first Exercise
		Exercise exercise = exercises.get(0);
		assertEquals("One", exercise.getName());
		assertEquals(description, exercise.getDescription());
		assertEquals(100, exercise.getBurntCalories(), 0);
		// assert second Exercise
		Exercise exercise2 = exercises.get(1);
		assertEquals("Two", exercise2.getName());
		assertEquals(longDescription, exercise2.getDescription());
		assertEquals(200, exercise2.getBurntCalories(), 0);
	}

	@Test
	public void shouldImportFoodsOverridingExistingByName() throws IOException {
		// GIVEN
		// two foods on DB
		// one that will be overridden
		FoodManager.it().createFood("Override me", 100d, 10d, 20d, 30d);
		// one that wont be
		FoodManager.it().createFood("I will survive", 100d, 10d, 20d, 30d);
		// a yaml file with two foods, one that exists (named "Override me") and another that is not on DB
		copyTestFile(FOOD);
		// WHEN
		// activity is started
		createActivity();
		// import of foods is requested
		clickOn(activity.findViewById(R.id.fileImportFoodButton));
		// THEN
		List<Food> foods = FoodManager.it().getAllFoods();
		assertEquals("There should be 3 foods in total", 3, foods.size());
		// assert overridden food that must have same id as before
		Food overridden = foods.get(0);
		assertEquals("Override me", overridden.getName());
		assertEquals(200d, overridden.getCalories(), 0);
		assertEquals(20d, overridden.getProtein(), 0);
		assertEquals(40d, overridden.getCarbs(), 0);
		assertEquals(0d, overridden.getFats(), 0);
		// assert survivor food that must have same id as before
		Food survivor = foods.get(1);
		assertEquals("I will survive", survivor.getName());
		assertEquals(100d, survivor.getCalories(), 0);
		assertEquals(10d, survivor.getProtein(), 0);
		assertEquals(20d, survivor.getCarbs(), 0);
		assertEquals(30d, survivor.getFats(), 0);
		// assert new food that must have new id
		Food newFood = foods.get(2);
		assertEquals("Pan", newFood.getName());
		assertEquals(250d, newFood.getCalories(), 0);
		assertEquals(10d, newFood.getProtein(), 0);
		assertEquals(150d, newFood.getCarbs(), 0);
		assertEquals(10d, newFood.getFats(), 0);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new FileSharingActivity();
	}

	// Private -------------------------------------------------------

	private void copyTestFile(FileSharingName name) throws IOException {
		File origin = new File("target/test-classes", name.getFileName());
		FileUtils.copyFile(origin, new File(getExternalDir(), name.getFileName()));
	}

	private File getExternalDir() {
		return new File(ShadowActivity.EXTERNAL_FILES_DIR, FileSharingName.DATA_DIR_NAME);
	}

	// Inner classes -------------------------------------------------
}
