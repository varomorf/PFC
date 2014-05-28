package com.gyp.pfc.activities.food;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * <p>
 * Activity for entering nutritional and identification data can be entered for
 * a food in order to save it for later use.
 * </p>
 * 
 * @author Alvaro
 * 
 */
public class AddFoodActivity extends OrmLiteBaseActivity<DatabaseHelper>  {

	// Constants -----------------------------------------------------

	public static final int FOOD_NAME = 1;

	// Attributes ----------------------------------------------------

	/** Helper to be used */
	private FoodActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new FoodActivityHelper(this);

		setContentView(R.layout.food_add_food);
	}

	/**
	 * Callback method for the saveFoodButton button of the add_food layout
	 * 
	 * @param view
	 *            The view
	 */
	public void saveFood(View view) {
		try {
			Food food = createFood();
			// save food on DB
			getHelper().getFoodDao().create(food);
			// show toast
			Toast.makeText(getApplicationContext(), R.string.newFoodInserted, Toast.LENGTH_SHORT).show();
			// close activity
			finish();
		} catch (MandatoryFieldNotFilledException e) {
			TextView field = (TextView) findViewById(e.getFieldId());
			field.requestFocus();
			field.setError(e.getErrorMessage());
			Log.v(AddFoodActivity.class.getName(), "Mandatory field not set", e);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Creates a new food with the data from the add_food form
	 * 
	 * @return the food created
	 */
	private Food createFood() {
		Food food = new Food();
		h.fillFoodData(food);
		return food;
	}

	// Inner classes -------------------------------------------------

}