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
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * <p>
 * With activity nutritional data can be entered for a food in order to qualify
 * it. Only nutritional info is entered on this activity, the name of the food
 * is entered in {@link EnterFoodNameActivity}.
 * </p>
 * 
 * @author Alvaro
 * 
 */
public class AddFoodActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	// TODO fucking comment this
	// Constants -----------------------------------------------------
	public static final int FOOD_NAME = 1;

	// Attributes ----------------------------------------------------
	// view components
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
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
		// Handle item selection on menu
		switch (item.getItemId()) {
		case R.id.mainMenuFoodsList:
			// show food list
			startActivity(new Intent(this, FoodsListActivity.class));
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
				// food name was correctly entered -> extract food name
				String foodName = data
						.getStringExtra(EnterFoodNameActivity.FOOD_NAME_IDENTIFIER);
				// save food with passed name
				saveFoodData(foodName);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * <p>
	 * Will be called when the calculate button is pressed.
	 * </p>
	 * <p>
	 * Food color will be calculated and showed in the same view.
	 * </p>
	 * 
	 * @param view
	 */
	public void calculate(View view) {
		food = new Food();
		extractDataFromViewComponents();
		if (filled) {
			updateUI();
		} else {
			clearResultsView();
			Toast.makeText(getApplicationContext(), R.string.notFilledAlert,
					Toast.LENGTH_SHORT).show();
		}
		caloriesEditText.requestFocus();
	}

	/**
	 * <p>
	 * Will be called when the add food button is pressed
	 * </p>
	 * 
	 * <p>
	 * This will launch the {@link EnterFoodNameActivity} to ask the user to
	 * enter the food's name. When this activity ends correctly the food data
	 * will be saved with the retrieved name.
	 * </p>
	 * 
	 * @param view
	 */
	public void enterFoodName(View view) {
		if (food != null && !food.isEmpty()) {
			Intent i = new Intent(this, EnterFoodNameActivity.class);
			startActivityForResult(i, FOOD_NAME);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	/**
	 * Creates a new food with the passed food name.
	 * 
	 * @param foodName
	 *            The name to be used for the food
	 */
	private void saveFoodData(String foodName) {
		food.setName(foodName);
		// save food on DB
		getHelper().getFoodDao().create(food);
		clearResultsView();
		// tell user that the food was correctly saved
		Toast.makeText(getApplicationContext(),
				foodName + " " + getString(R.string.newFoodInserted),
				Toast.LENGTH_SHORT).show();
	}

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

	private void extractDataFromViewComponents() {
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

	private void updateUI() {
		int sugarPercentage = food.getSugarPercentage();
		int fatsPercentage = food.getFatsPercentage();
		resultSugarsText.setText(getString(R.string.resultSugarsText) + ": "
				+ sugarPercentage + "% (" + food.getSugarCalories() + " KCal)");
		resultSugarsText.setBackgroundColor(food.getSugarColor());
		resultFatsText.setText(getString(R.string.resultFatsText) + ": "
				+ fatsPercentage + "% (" + food.getFatsCalories() + " KCal)");
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

	private void clearResultsView() {
		resultSugarsText.setText("");
		resultFatsText.setText("");
		resultFinalText.setText("");
		resultSugarsText.setBackgroundColor(Color.WHITE);
		resultFatsText.setBackgroundColor(Color.WHITE);
		resultFinalText.setBackgroundColor(Color.WHITE);
		addFoodButton.setVisibility(View.INVISIBLE);
	}
	// Inner classes -------------------------------------------------

}