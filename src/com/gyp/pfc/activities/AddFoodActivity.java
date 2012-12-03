/**
 * 
 */
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
public class AddFoodActivity extends Activity {

	public static final String FOOD_NAME_IDENTIFIER = "foodName";

	private EditText foodNameTextEdit;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);

		getUIForms();
	}

	/**
	 * 
	 */
	private void getUIForms() {
		foodNameTextEdit = (EditText) findViewById(R.id.foodNameTextEdit);
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
}
