package com.gyp.pfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;

/**
 * @author afernandezgo
 * 
 */
public class ShowFoodDetailsActivity extends Activity {
	// TODO fucking comment this
	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private TextView nameText;
	private TextView caloriesText;
	private TextView sugarsText;
	private TextView fatsText;

	private Food food;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_food_details);

		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(
				FoodsListActivity.SELECTED_FOOD);
		getUIForms();
		updateUI();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private void getUIForms() {
		nameText = (TextView) findViewById(R.id.nameTextView);
		caloriesText = (TextView) findViewById(R.id.caloriesText);
		sugarsText = (TextView) findViewById(R.id.sugarsText);
		fatsText = (TextView) findViewById(R.id.fatsText);
	}

	private void updateUI() {
		nameText.setText(food.getName());
		nameText.setTextColor(food.getColor());
		caloriesText.setText(food.getCalories() + " KCal");
		sugarsText.setText(food.getSugars() + " gr. / " + food.sugarCalories()
				+ " KCal - " + food.sugarPercentage() + "%");
		sugarsText.setBackgroundColor(food.getSugarColor());
		fatsText.setText(food.getFats() + " gr. / " + food.fatsCalories()
				+ " Kcal - " + food.fatsPercentage() + "%");
		fatsText.setBackgroundColor(food.getFatsColor());
	}
	// Inner classes -------------------------------------------------

}
