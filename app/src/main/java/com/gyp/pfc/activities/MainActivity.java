package com.gyp.pfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.biometric.AddWeightActivity;
import com.gyp.pfc.activities.biometric.ShowUserBiometricDataActivity;
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
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.data.domain.manager.MealNameManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.gyp.pfc.data.domain.manager.WeightManager;
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

	public void biometricButton(View view) {
		startActivity(new Intent(this, ShowUserBiometricDataActivity.class));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

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
		TrainingManager.it().setTrainingDao(getHelper().getTrainingDao());
		TrainingManager.it().setTrainingExerciseDao(getHelper().getTrainingExerciseDao());
		FoodManager.it().setFoodDao(getHelper().getFoodDao());
		ExerciseManager.it().setExerciseDao(getHelper().getExerciseDao());
		MealNameManager.getInstance().setMealNameDao(getHelper().getMealNameDao());
		WeightManager.it().setWeightDao(getHelper().getWeightDao());
	}

	// Inner classes -------------------------------------------------

}
