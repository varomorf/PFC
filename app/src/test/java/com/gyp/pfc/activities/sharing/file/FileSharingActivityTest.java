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
		ExerciseManager.it().setExerciseDao(dbh.getExerciseDao());
		TrainingManager.it().setTrainingExerciseDao(dbh.getTrainingExerciseDao());
		TrainingManager.it().setTrainingDao(dbh.getTrainingDao());
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
		Exercise e1 = ExerciseManager.it().createExercise("One", "", 100);
		Exercise e2 = ExerciseManager.it().createExercise("Two", "", 200);
		Training t1 = TrainingManager.it().createTraining("One");
		Training t2 = TrainingManager.it().createTraining("Two");
		TrainingManager.it().addExerciseToTraining(t1, e1, 10, 1);
		TrainingManager.it().addExerciseToTraining(t2, e1, 20, 2);
		TrainingManager.it().addExerciseToTraining(t2, e2, 30, 3);
		// one not executable training
		TrainingManager.it().createTraining("Three", false);
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
		ExerciseManager.it().createExercise("One", "With short description", 100);
		ExerciseManager.it().createExercise("Two", longDescription, 200);
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
		// one that will be overwritten
		FoodManager.it().createFood("Override me", 100d, 10d, 20d, 30d);
		// one that wont be
		FoodManager.it().createFood("I will survive", 100d, 10d, 20d, 30d);
		// a yaml file with two foods, one that exists (named "Override me") and another that is not on DB
		copyExternalTestFile(FOOD);
		// WHEN
		// activity is started
		createActivity();
		// import of foods is requested
		clickOn(activity.findViewById(R.id.fileImportFoodButton));
		// THEN
		List<Food> foods = FoodManager.it().getAllFoods();
		assertEquals("There should be 3 foods in total", 3, foods.size());
		// assert overwritten food that must have same id as before
		Food overwritten = foods.get(0);
		assertEquals("Override me", overwritten.getName());
		assertEquals(200d, overwritten.getCalories(), 0);
		assertEquals(20d, overwritten.getProtein(), 0);
		assertEquals(40d, overwritten.getCarbs(), 0);
		assertEquals(0d, overwritten.getFats(), 0);
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

	@Test
	public void shouldImportExercisesOverridinByName() throws EntityNameException, IOException {
		// GIVEN
		// two exercises on DB
		// one that will be overwritten
		ExerciseManager.it().createExercise("Override me", "", 100);
		// one that wont be
		ExerciseManager.it().createExercise("I will survive", "Yes I will", 100);
		// a yaml file with two exercises, one that exists (named "Override me") and another that is not on DB
		copyExternalTestFile(EXERCISE);
		// WHEN
		// activity is started
		createActivity();
		// import of exercises is requested
		clickOn(activity.findViewById(R.id.fileImportExerciseButton));
		// THEN
		List<Exercise> exercises = ExerciseManager.it().getAllExercises();
		assertEquals("There should be 3 exercises in total", 3, exercises.size());
		// assert overwritten exercise that must have same id as before
		Exercise overwritten = exercises.get(0);
		assertEquals("Override me", overwritten.getName());
		assertEquals("Overriden description", overwritten.getDescription());
		assertEquals(500d, overwritten.getBurntCalories(), 0);
		// assert survivor exercise that must have same id as before
		Exercise survivor = exercises.get(1);
		assertEquals("I will survive", survivor.getName());
		assertEquals("Yes I will", survivor.getDescription());
		assertEquals(100d, survivor.getBurntCalories(), 0);
		// assert new exercise that must have new id
		Exercise newExercise = exercises.get(2);
		assertEquals("Flexiones", newExercise.getName());
		assertEquals("Hacer flexiones XD", newExercise.getDescription());
		assertEquals(200d, newExercise.getBurntCalories(), 0);
	}

	@Test
	public void shouldImportTrainingsOverridingExistingByNameAndAddingExercises() throws EntityNameException,
			IOException {
		// GIVEN
		// one exercises on DB that must not be overwritten
		Exercise e = ExerciseManager.it().createExercise("Exercise", "Pre-existent", 100);
		// two executable trainings on DB. One will be overwritten
		Training t1 = TrainingManager.it().createTraining("I won't");
		TrainingManager.it().addExerciseToTraining(t1, e, 10, 1);
		Training t2 = TrainingManager.it().createTraining("I will be overwritten");
		TrainingManager.it().addExerciseToTraining(t2, e, 20, 2);
		TrainingManager.it().addExerciseToTraining(t2, e, 30, 3);
		// a yaml file with two trainings, one that uses existing exercise and other that uses new exercise
		copyExternalTestFile(TRAINING);
		// WHEN
		// activity is started
		createActivity();
		// import of exercises is requested
		clickOn(activity.findViewById(R.id.fileImportTrainingButton));
		// THEN
		List<Training> trainings = TrainingManager.it().getAllTrainings();
		assertEquals("There should be 3 trainings in total", 3, trainings.size());
		List<Exercise> exercises = ExerciseManager.it().getAllExercises();
		assertEquals("There should be 2 exercises in total", 2, exercises.size());
		// first assert the new exercise
		Exercise newExercise = exercises.get(1);
		assertEquals("Nuevo", newExercise.getName());
		assertEquals("Soy nuevo", newExercise.getDescription());
		assertEquals(200d, newExercise.getBurntCalories(), 0);
		// assert not overwritten training
		Training notOverwritten = trainings.get(0);
		assertEquals("I won't", notOverwritten.getName());
		assertTrue(notOverwritten.isExecutable());
		assertEquals(1, notOverwritten.getExercises().size());
		TrainingExercise te1 = (TrainingExercise) notOverwritten.getExercises().toArray()[0];
		assertEquals(e.getName(), te1.getExercise().getName());
		assertEquals(10, te1.getSeconds());
		assertEquals(1, te1.getReps());
		// assert overwritten training (now it uses the new exercise)
		Training overwritten = trainings.get(1);
		assertEquals("I will be overwritten", overwritten.getName());
		assertTrue(overwritten.isExecutable());
		assertEquals(1, overwritten.getExercises().size());
		TrainingExercise te2 = (TrainingExercise) overwritten.getExercises().toArray()[0];
		assertEquals(newExercise.getName(), te2.getExercise().getName());
		assertEquals(60, te2.getSeconds());
		assertEquals(10, te2.getReps());
		// assert new training (uses the previous exercise 2 times)
		Training newTraining = trainings.get(2);
		assertEquals("New training", newTraining.getName());
		assertTrue(newTraining.isExecutable());
		assertEquals(2, newTraining.getExercises().size());
		TrainingExercise te3 = (TrainingExercise) newTraining.getExercises().toArray()[0];
		assertEquals(e.getName(), te3.getExercise().getName());
		assertEquals(10, te3.getSeconds());
		assertEquals(10, te3.getReps());
		TrainingExercise te4 = (TrainingExercise) newTraining.getExercises().toArray()[1];
		assertEquals(e.getName(), te4.getExercise().getName());
		assertEquals(20, te4.getSeconds());
		assertEquals(10, te4.getReps());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new FileSharingActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
