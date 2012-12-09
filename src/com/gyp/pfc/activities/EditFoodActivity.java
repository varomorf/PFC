package com.gyp.pfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
public class EditFoodActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------
	/**
	 * Result code for food edition
	 */
	public static final int EDIT_FOOD = 1;
	/**
	 * Key for the selected food
	 */
	public static final String FOOD_TO_EDIT = "selectedFood";

	// Attributes ----------------------------------------------------

	private TextView foodName;
	private TextView foodCalories;
	private TextView foodSugars;
	private TextView foodFats;
	private Food food;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_food);
		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(FOOD_TO_EDIT);
		getUIForms();
		updateUI();
	}

	/**
	 * Method to be called by the Edit button
	 * 
	 * @param view
	 *            The view
	 */
	public void editFoodButton(View view) {
		// set new food values
		food.setName(foodName.getText().toString());
		food.setCalories(Integer.parseInt(foodCalories.getText().toString()));
		food.setSugars(Integer.parseInt(foodSugars.getText().toString()));
		food.setFats(Integer.parseInt(foodFats.getText().toString()));
		// update the food on DB
		getHelper().getFoodDao().update(food);
		// prepare intent for return
		Intent i = new Intent();
		i.putExtra(FOOD_TO_EDIT, food);
		setResult(RESULT_OK, i);
		// return
		finish();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private void getUIForms() {
		foodName = (TextView) findViewById(R.id.foodName);
		foodCalories = (TextView) findViewById(R.id.foodCalories);
		foodSugars = (TextView) findViewById(R.id.foodSugars);
		foodFats = (TextView) findViewById(R.id.foodFats);
	}

	private void updateUI() {
		foodName.setText(food.getName());
		foodCalories.setText(Integer.toString(food.getCalories()));
		foodSugars.setText(Integer.toString(food.getSugars()));
		foodFats.setText(Integer.toString(food.getFats()));
	}

	// Inner classes -------------------------------------------------

}
