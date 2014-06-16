package com.gyp.pfc.activities.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for the edition of a food
 * 
 * @author Alvaro
 * 
 */
public class EditFoodActivity extends OrmLiteBaseActivity<DatabaseHelper> implements FoodConstants{

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Food being edited on the activity */
	private Food food;

	/** Helper to be used */
	private FoodActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new FoodActivityHelper(this);

		setContentView(R.layout.food_add_food);
		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(SELECTED_FOOD);
		updateUI();
	}

	/**
	 * Method to be called by the commit button
	 * 
	 * @param view
	 *            The view
	 */
	public void saveFood(View view) {
		h.fillFoodData(food);
		// update the food on DB
		getHelper().getFoodDao().update(food);
		// prepare intent for return
		setResult(RESULT_OK);
		// return
		finish();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Fills the UI with the data from the activity's food
	 */
	private void updateUI() {
		h.setTextToUI(R.id.foodNameText, food.getName());
		h.setTextToUI(R.id.caloriesText, food.getCalories().toString());
		h.setTextToUI(R.id.proteinsText, food.getProtein().toString());
		h.setTextToUI(R.id.carbsText, food.getCarbs().toString());
		h.setTextToUI(R.id.fatsText, food.getFats().toString());
		h.setTextToUI(R.id.foodBrandText, food.getBrandName());
		h.setTextToUI(R.id.sugarText, food.getSugar().toString());
		h.setTextToUI(R.id.fiberText, food.getFiber().toString());
		h.setTextToUI(R.id.saturatedFatsText, food.getSaturatedFats().toString());
		h.setTextToUI(R.id.sodiumText, food.getSodium().toString());
	}

	// Inner classes -------------------------------------------------

}
