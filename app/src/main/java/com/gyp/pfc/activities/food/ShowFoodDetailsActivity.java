package com.gyp.pfc.activities.food;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
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

	private Food food;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_details);

		// retrieve the food to be shown from the intent
		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(
				EditFoodActivity.FOOD_TO_EDIT);
		updateUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.crud_context_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			deleteWithDialog();
			return true;
		case R.id.edit:
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

	private void updateUI() {
		UIUtils.setTextToUI(findViewById(R.id.foodDetailsName), food.getName());
		UIUtils.setTextToUI(findViewById(R.id.caloriesText),
				Double.toString(food.getCalories()));
		UIUtils.setTextToUI(findViewById(R.id.sugarsText),
				Double.toString(food.getSugar()) + "g");
		UIUtils.setTextToUI(findViewById(R.id.fatsText),
				Double.toString(food.getFats()) + "g");
	}

	private void editFood() {
		Intent editFoodIntent = new Intent(getApplicationContext(),
				EditFoodActivity.class);
		editFoodIntent.putExtra(EditFoodActivity.FOOD_TO_EDIT, food);
		startActivityForResult(editFoodIntent, EditFoodActivity.EDIT_FOOD);
	}
	
	private void deleteWithDialog(){
		new AlertDialog.Builder(this)
		.setMessage(R.string.assureFoodDeletion)
		.setCancelable(false)
		.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//deletion is confirmed -> delete
						getHelper().getFoodDao().delete(food);
						finish();
					}
				})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//do not delete -> close dialog
				dialog.cancel();
			}
		}).create().show();
	}

	// Inner classes -------------------------------------------------

}
