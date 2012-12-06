package com.gyp.pfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;

public class AddFoodActivity extends Activity {
	// TODO fucking comment this
	// Constants -----------------------------------------------------
	public static final int FOOD_NAME = 1;

	// Attributes ----------------------------------------------------
	private EditText caloriesEditText;
	private EditText sugarsEditText;
	private EditText fatsEditText;
	private TextView resultSugarsText;
	private TextView resultFatsText;
	private TextView resultFinalText;
	private Button addFoodButton;

	private boolean filled;
	private Food food;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);

		DatabaseHelper.createInstance(getApplicationContext());
		getUIForms();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.mainMenuFoodsList:
			foodsList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case FOOD_NAME:
			if (resultCode == Activity.RESULT_OK) {
				insertFood(data);
			}
			break;

		default:
			break;
		}
	}

	public void calculate(View view) {
		food = new Food();
		extract();
		if (filled) {
			updateUI();
		} else {
			clearResults();
			Toast.makeText(getApplicationContext(), R.string.notFilledAlert,
					Toast.LENGTH_SHORT).show();
		}
		caloriesEditText.requestFocus();
	}

	public void addFood(View view) {
		if (food != null && !food.isEmpty()) {
			Intent i = new Intent(this, EnterFoodNameActivity.class);
			startActivityForResult(i, FOOD_NAME);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	/**
	 * @param data
	 */
	private void insertFood(Intent data) {
		String foodName = data
				.getStringExtra(EnterFoodNameActivity.FOOD_NAME_IDENTIFIER);
		food.setName(foodName);
		DatabaseHelper.getFoodManager().insertFood(food);
		clearResults();
		Toast.makeText(getApplicationContext(),
				foodName + " " + getString(R.string.newFoodInserted),
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 */
	private void getUIForms() {
		caloriesEditText = (EditText) findViewById(R.id.caloriesText);
		sugarsEditText = (EditText) findViewById(R.id.sugarsText);
		fatsEditText = (EditText) findViewById(R.id.fatsText);
		resultSugarsText = (TextView) findViewById(R.id.resultSugarsText);
		resultFatsText = (TextView) findViewById(R.id.resultFatsText);
		resultFinalText = (TextView) findViewById(R.id.resultFinalText);
		addFoodButton = (Button) findViewById(R.id.addFoodButton);

		resultSugarsText.setTextColor(Color.BLACK);
		resultFatsText.setTextColor(Color.BLACK);
		resultFinalText.setTextColor(Color.BLACK);
	}

	private void foodsList() {
		Intent i = new Intent(this, FoodsListActivity.class);
		startActivity(i);
	}

	/**
	 * 
	 */
	private void extract() {
		String caloriesValue = caloriesEditText.getText().toString();
		String fatsValue = fatsEditText.getText().toString();
		String sugarsValue = sugarsEditText.getText().toString();
		filled = false;
		if (caloriesValue.length() != 0 && fatsValue.length() != 0
				&& sugarsValue.length() != 0) {
			food.setCalories(Integer.parseInt(caloriesValue));
			food.setSugars(Integer.parseInt(sugarsValue));
			food.setFats(Integer.parseInt(fatsValue));
			filled = true;
		}
	}

	/**
	 * 
	 */
	private void updateUI() {
		int sugarPercentage = food.sugarPercentage();
		int fatsPercentage = food.fatsPercentage();
		resultSugarsText.setText(getString(R.string.resultSugarsText) + ": "
				+ sugarPercentage + "% (" + food.sugarCalories() + " KCal)");
		resultSugarsText.setBackgroundColor(food.getSugarColor());
		resultFatsText.setText(getString(R.string.resultFatsText) + ": "
				+ fatsPercentage + "% (" + food.fatsCalories() + " KCal)");
		resultFatsText.setBackgroundColor(food.getFatsColor());
		String finalText = getString(R.string.resultFinalText) + " ";
		switch (food.getColor()) {
		case Color.GREEN:
			finalText += getString(R.string.green);
			break;
		case Color.YELLOW:
			finalText += getString(R.string.yellow);
			break;
		case Color.RED:
			finalText += getString(R.string.red);
			break;
		}
		resultFinalText.setText(finalText);
		resultFinalText.setBackgroundColor(food.getColor());
		// set to initial state
		caloriesEditText.setText("");
		sugarsEditText.setText("");
		fatsEditText.setText("");
		addFoodButton.setVisibility(View.VISIBLE);
	}

	private void clearResults() {
		resultSugarsText.setText("");
		resultFatsText.setText("");
		resultFinalText.setText("");
		resultSugarsText.setBackgroundColor(Color.BLACK);
		resultFatsText.setBackgroundColor(Color.BLACK);
		resultFinalText.setBackgroundColor(Color.BLACK);
		addFoodButton.setVisibility(View.INVISIBLE);
	}
	// Inner classes -------------------------------------------------

}