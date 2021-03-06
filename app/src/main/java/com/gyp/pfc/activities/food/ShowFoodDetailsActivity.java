package com.gyp.pfc.activities.food;

import org.apache.commons.lang.StringUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.constants.FoodConstants;
import com.gyp.pfc.activities.helpers.FoodActivityHelper;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.food.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for showing the details of a {@link Food} entity
 * 
 * @author Alvaro
 * 
 */
public class ShowFoodDetailsActivity extends OrmLiteBaseActivity<DatabaseHelper> implements FoodConstants {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** The food which details are shown */
	private Food food;

	/** Helper to be used */
	private FoodActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new FoodActivityHelper(this);
		setContentView(R.layout.food_details);

		// retrieve the food to be shown from the intent
		Intent sender = getIntent();
		food = (Food) sender.getExtras().getSerializable(SELECTED_FOOD);
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
			deleteFood();
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
		if (resultCode == RESULT_OK) {
			onFoodEdited(data);
		}
	}

	// Private -------------------------------------------------------

	/**
	 * Fills the GUI widgets with the food data
	 */
	private void updateUI() {
		// obligatory fields
		h.setTextToUI(R.id.foodDetailsName, getCompleteFoodName());
		h.setTextToUI(R.id.caloriesText, food.getCalories().toString());
		setGramsField(R.id.proteinsText, food.getProtein());
		setGramsField(R.id.carbsText, food.getCarbs());
		setGramsField(R.id.fatsText, food.getFats());
		// rest of the fields
		setGramsField(R.id.sugarText, food.getSugar());
		setGramsField(R.id.fiberText, food.getFiber());
		setGramsField(R.id.saturatedFatsText, food.getSaturatedFats());
		setMilligramsField(R.id.sodiumText, food.getSodium());
	}

	/**
	 * Starts the activity to edit the shown food
	 */
	private void editFood() {
		Intent editFoodIntent = new Intent(getApplicationContext(), EditFoodActivity.class);
		editFoodIntent.putExtra(SELECTED_FOOD, food);
		startActivityForResult(editFoodIntent, EDIT_FOOD);
	}

	/**
	 * Sets the GUI TextView with the passed id, the specified value representing grams. If the passed value is
	 * <code>null</code>, <b>0</b> will be used.
	 * 
	 * @param fieldId
	 *            the id of the TextView which text will be set
	 * @param value
	 *            the value to be set to the TextView
	 */
	private void setGramsField(int fieldId, Double value) {
		h.setTextToUI(fieldId, value.toString());
	}

	/**
	 * Sets the GUI TextView with the passed id, the specified value representing milligrams. If the passed value
	 * is <code>null</code>, <b>0</b> will be used.
	 * 
	 * @param fieldId
	 *            the id of the TextView which text will be set
	 * @param value
	 *            the value to be set to the TextView
	 */
	private void setMilligramsField(int fieldId, Double value) {
		Double finalValue = new Double(value * 1000);
		h.setTextToUI(fieldId, finalValue.toString());
	}

	/**
	 * Callback for when a food has been correctly edited
	 * 
	 * @param data
	 *            the data from the edition
	 */
	private void onFoodEdited(Intent data) {
		// get updated food from db
		getHelper().getFoodDao().refresh(food);
		// refresh UI
		updateUI();
		Toast.makeText(getApplicationContext(), getString(R.string.editFoodMessage), Toast.LENGTH_SHORT).show();
	}

	/**
	 * Returns the complete food name including the brand name if present
	 * 
	 * @return the complete food name including the brand name if present
	 */
	private String getCompleteFoodName() {
		String completeName = food.getName();
		if (StringUtils.isNotBlank(food.getBrandName())) {
			completeName += " - " + food.getBrandName();
		}
		return completeName;
	}

	/**
	 * Delete the shown food via a confirmation dialog
	 */
	private void deleteFood() {
		h.deleteWithDialog(R.string.assureFoodDeletion, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// deletion is confirmed -> delete and close
				getHelper().getFoodDao().delete(food);
				finish();
			}
		});
	}

	// Inner classes -------------------------------------------------

}
