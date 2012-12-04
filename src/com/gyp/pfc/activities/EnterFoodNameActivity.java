package com.gyp.pfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gyp.pfc.R;

/**
 * @author afernandezgo
 * 
 */
public class EnterFoodNameActivity extends Activity {
	// TODO fucking comment this
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

	public void addFoodButton(View view) {
		String foodName = foodNameTextEdit.getText().toString().trim();
		if (foodName.length() != 0) {
			Intent i = new Intent();
			i.putExtra(FOOD_NAME_IDENTIFIER, foodName);
			setResult(RESULT_OK, i);
			finish();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	/**
	 * 
	 */
	private void getUIForms() {
		foodNameTextEdit = (EditText) findViewById(R.id.foodNameTextEdit);
	}

	// Inner classes -------------------------------------------------

}
