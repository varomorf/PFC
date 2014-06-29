package com.gyp.pfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.biometric.AddWeightActivity;
import com.gyp.pfc.activities.biometric.WeightListActivity;
import com.gyp.pfc.activities.exercise.AddExerciseActivity;
import com.gyp.pfc.activities.exercise.ExerciseListActivity;
import com.gyp.pfc.activities.food.AddFoodActivity;
import com.gyp.pfc.activities.food.FoodListActivity;
import com.gyp.pfc.activities.historic.AddTrainingHistoricActivity;
import com.gyp.pfc.activities.historic.TrainingHistoricListActivity;
import com.gyp.pfc.activities.meal.EditMealActivity;
import com.gyp.pfc.activities.meal.MealListActivity;
import com.gyp.pfc.activities.sharing.file.FileSharingActivity;
import com.gyp.pfc.activities.training.AddTrainingActivity;
import com.gyp.pfc.activities.training.TrainingListActivity;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.data.domain.manager.MealNameManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Main activity of the application. Holds the list of the different actions.
 * 
 * @author Alvaro
 * 
 */
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initializeManagers();
		try {
			// TODO change me
			loadTestData();
		} catch (EntityNameException e) {
			// NOOP
		}
		loadDefaultMeals();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void foodsAddButton(View view) {
		startActivity(new Intent(this, AddFoodActivity.class));
	}

	public void foodsListButton(View view) {
		startActivity(new Intent(this, FoodListActivity.class));
	}

	public void mealsAddButton(View view) {
		startActivity(new Intent(this, EditMealActivity.class));
	}

	public void mealsListButton(View view) {
		startActivity(new Intent(this, MealListActivity.class));
	}

	public void exercisesAddButton(View view) {
		startActivity(new Intent(this, AddExerciseActivity.class));
	}

	public void exercisesListButton(View view) {
		startActivity(new Intent(this, ExerciseListActivity.class));
	}

	public void trainningsAddButton(View view) {
		startActivity(new Intent(this, AddTrainingActivity.class));
	}

	public void trainningsListButton(View view) {
		startActivity(new Intent(this, TrainingListActivity.class));
	}

	public void trainningHisctoricAddButton(View view) {
		startActivity(new Intent(this, AddTrainingHistoricActivity.class));
	}

	public void trainningHisctoricListButton(View view) {
		startActivity(new Intent(this, TrainingHistoricListActivity.class));
	}

	public void weightsAddButton(View view) {
		startActivity(new Intent(this, AddWeightActivity.class));
	}

	public void weightsListButton(View view) {
		startActivity(new Intent(this, WeightListActivity.class));
	}

	public void sharingButton(View view) {
		startActivity(new Intent(this, FileSharingActivity.class));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected void loadTestData() throws EntityNameException {
		// load test food
		FoodManager.it().createFood("Arroz", "", 364, 6.67, 81.60, 0, 1.4, 0.9, 0.19, 0.0039);
		FoodManager.it().createFood("Huevo", "", 162, 12.68, 0.68, 0, 0, 12.10, 3.3, 0.144);
		FoodManager.it()
				.createFood("Pan blanco", "Mercadona", 261, 8.47, 51.50, 0, 3.5, 1.6, 0.39, 0.540);
		// load test exercises
		Exercise exercise1 = ExerciseManager
				.getInstance()
				.createExercise(
						"Planchas",
						"Boca abajo, apoyamos las manos contra el suelo a la altura de los hombros y elevamos el cuerpo mediante una extensión de los brazos. Mediante una flexión controlada, volvemos a la posición de partida.",
						50);
		Exercise exercise2 = ExerciseManager
				.getInstance()
				.createExercise(
						"Abdominales",
						"Boca arriba, flexionamos las piernas de modo que los pies toquen el suelo. Elevamos el tronco hasta erguirnos. Bajamos el tronco a la posición de partida de forma controlada.",
						100);
		Exercise exercise3 = ExerciseManager
				.getInstance()
				.createExercise(
						"Zancadas",
						"De pie, damos un paso con una pierna, dejando la otra en el sitio. Flexionamos la rodilla adelantada, de tal modo que la rodilla atrasada llegue a tocar el suelo. Volvemos a la posición de partida.",
						150);
		// load test trainings
		Training training1 = TrainingManager.getInstance().createTraining("Pirámide de abdominales");
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 20);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 30);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 40);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 30);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 20);
		Training training2 = TrainingManager.getInstance().createTraining("Serie de flexiones");
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		Training training3 = TrainingManager.getInstance().createTraining("Serie variada");
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise1, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise2, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise3, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise1, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise2, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise3, 30, 1);
	}

	/**
	 * Loads the default meal names
	 */
	protected void loadDefaultMeals() {
		MealNameManager.getInstance().createMealNameIfOrderNotPresent(getString(R.string.breakfast), 1);
		MealNameManager.getInstance().createMealNameIfOrderNotPresent(getString(R.string.lunch), 2);
		MealNameManager.getInstance().createMealNameIfOrderNotPresent(getString(R.string.dinner), 3);
		MealNameManager.getInstance().createMealNameIfOrderNotPresent(getString(R.string.snacks), 4);
	}

	// Private -------------------------------------------------------

	/**
	 * Initializes all managers
	 */
	private void initializeManagers() {
		TrainingManager.getInstance().setTrainingDao(getHelper().getTrainingDao());
		TrainingManager.getInstance().setTrainingExerciseDao(getHelper().getTrainingExerciseDao());
		FoodManager.it().setFoodDao(getHelper().getFoodDao());
		ExerciseManager.getInstance().setExerciseDao(getHelper().getExerciseDao());
		MealNameManager.getInstance().setMealNameDao(getHelper().getMealNameDao());
	}

	// Inner classes -------------------------------------------------

}
