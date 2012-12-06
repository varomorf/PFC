package com.gyp.pfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class EditFoodActivity extends OrmLiteBaseActivity<DatabaseHelper> {

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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_food);
		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(
				FoodsListActivity.SELECTED_FOOD);
		getUIForms();
		updateUI();
	}

	public void editFoodButton(View view) {
		// FIXME is not working
		food.setName(nameText.getText().toString());
		food.setCalories(Integer.parseInt(caloriesText.getText().toString()));
		food.setSugars(Integer.parseInt(sugarsText.getText().toString()));
		food.setFats(Integer.parseInt(fatsText.getText().toString()));
		getHelper().getFoodDao().update(food);
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private void getUIForms() {
		nameText = (TextView) findViewById(R.id.foodName);
		caloriesText = (TextView) findViewById(R.id.foodCalories);
		sugarsText = (TextView) findViewById(R.id.foodSugars);
		fatsText = (TextView) findViewById(R.id.foodFats);
	}

	private void updateUI() {
		nameText.setText(food.getName());
		caloriesText.setText(Integer.toString(food.getCalories()));
		sugarsText.setText(Integer.toString(food.getSugars()));
		fatsText.setText(Integer.toString(food.getFats()));
	}

	// Inner classes -------------------------------------------------

}
