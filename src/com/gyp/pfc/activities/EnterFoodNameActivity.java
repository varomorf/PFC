package com.gyp.pfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;

/**
 * {@link Activity} for entering the name of a {@link Food} entity
 * 
 * @author Alvaro
 * 
 */
public class EnterFoodNameActivity extends Activity {

	// Constants -----------------------------------------------------
	public static final String FOOD_NAME_IDENTIFIER = "foodName";

	// Attributes ----------------------------------------------------

	private EditText foodNameTextEdit;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_food_name);

		getUIForms();
	}

	/**
	 * Method for the Add food button
	 * 
	 * @param view
	 */
	public void addFoodButton(View view) {
		// get name from the widget
		String foodName = foodNameTextEdit.getText().toString().trim();
		if (foodName.length() != 0) {
			// prepare return intent
			Intent i = new Intent();
			// put name on the intent
			i.putExtra(FOOD_NAME_IDENTIFIER, foodName);
			// set result to OK
			setResult(RESULT_OK, i);
			// finish activity to return data
			finish();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private void getUIForms() {
		foodNameTextEdit = (EditText) findViewById(R.id.foodNameTextEdit);
	}

	// Inner classes -------------------------------------------------

}
