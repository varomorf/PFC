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

	/**
	 * Starts the {@link AddFoodActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void foodsAddButton(View view) {
		startActivity(new Intent(this, AddFoodActivity.class));
	}

	/**
	 * Starts the {@link FoodListActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void foodsListButton(View view) {
		startActivity(new Intent(this, FoodListActivity.class));
	}

	/**
	 * Starts the {@link EditMealActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void mealsAddButton(View view) {
		startActivity(new Intent(this, EditMealActivity.class));
	}

	/**
	 * Starts the {@link MealListActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void mealsListButton(View view) {
		startActivity(new Intent(this, MealListActivity.class));
	}

	/**
	 * Starts the {@link AddExerciseActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void exercisesAddButton(View view) {
		startActivity(new Intent(this, AddExerciseActivity.class));
	}

	/**
	 * Starts the {@link ExerciseListActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void exercisesListButton(View view) {
		startActivity(new Intent(this, ExerciseListActivity.class));
	}

	/**
	 * Starts the {@link AddTrainingActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void trainningsAddButton(View view) {
		startActivity(new Intent(this, AddTrainingActivity.class));
	}

	/**
	 * Starts the {@link TrainingListActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void trainningsListButton(View view) {
		startActivity(new Intent(this, TrainingListActivity.class));
	}

	/**
	 * Starts the {@link AddTrainingHistoricActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void trainningHisctoricAddButton(View view) {
		startActivity(new Intent(this, AddTrainingHistoricActivity.class));
	}

	/**
	 * Starts the {@link TrainingHistoricListActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void trainningHisctoricListButton(View view) {
		startActivity(new Intent(this, TrainingHistoricListActivity.class));
	}

	/**
	 * Starts the {@link AddWeightActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void weightsAddButton(View view) {
		startActivity(new Intent(this, AddWeightActivity.class));
	}

	/**
	 * Starts the {@link WeightListActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void weightsListButton(View view) {
		startActivity(new Intent(this, WeightListActivity.class));
	}

	/**
	 * Starts the {@link FileSharingActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
	public void sharingButton(View view) {
		startActivity(new Intent(this, FileSharingActivity.class));
	}

	/**
	 * Starts the {@link ShowUserBiometricDataActivity}
	 * 
	 * @param view
	 *            the button that launched this event
	 */
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
