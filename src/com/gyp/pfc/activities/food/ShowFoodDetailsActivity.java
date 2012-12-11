package com.gyp.pfc.activities.food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for showing the details of a {@link Food} entity
 * 
 * @author Alvaro
 * 
 */
public class ShowFoodDetailsActivity extends
		OrmLiteBaseActivity<DatabaseHelper> {

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
		setContentView(R.layout.show_food_details);

		// retrieve the food to be shown from the intent
		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(
				EditFoodActivity.FOOD_TO_EDIT);
		getUIForms();
		updateUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.foods_list_context_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.deleteFood:
			getHelper().getFoodDao().delete(food);
			finish();
			return true;
		case R.id.editFood:
			editFood();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case EditFoodActivity.EDIT_FOOD:
			if (resultCode == Activity.RESULT_OK) {
				// get updated food from intent
				food = (Food) data.getExtras().get(
						EditFoodActivity.FOOD_TO_EDIT);
				// refresh UI
				updateUI();
				Toast.makeText(getApplicationContext(),
						getString(R.string.editFoodMessage), Toast.LENGTH_SHORT)
						.show();
			}
			break;
		}
	}

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
		sugarsText.setText(food.getSugars() + " gr. / "
				+ food.getSugarCalories() + " KCal - "
				+ food.getSugarPercentage() + "%");
		sugarsText.setBackgroundColor(food.getSugarColor());
		fatsText.setText(food.getFats() + " gr. / " + food.getFatsCalories()
				+ " Kcal - " + food.getFatsPercentage() + "%");
		fatsText.setBackgroundColor(food.getFatsColor());
	}

	private void editFood() {
		Intent editFoodIntent = new Intent(getApplicationContext(),
				EditFoodActivity.class);
		editFoodIntent.putExtra(EditFoodActivity.FOOD_TO_EDIT, food);
		startActivityForResult(editFoodIntent, EditFoodActivity.EDIT_FOOD);
	}
	// Inner classes -------------------------------------------------

}
