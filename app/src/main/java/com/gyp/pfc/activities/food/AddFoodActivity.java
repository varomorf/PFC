package com.gyp.pfc.activities.food;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
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
public class AddFoodActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	public static final int FOOD_NAME = 1;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
	}

	/**
	 * Callback method for the saveFoodButton button of the add_food layout
	 * 
	 * @param view
	 *            The view
	 */
	public void saveFood(View view) {
		// get new food data
		Food food = new Food();
		food.setName(UIUtils.getTextFromUI(findViewById(R.id.foodNameText)));
		food.setBrandName(UIUtils.getTextFromUI(findViewById(R.id.foodBrandText)));
		food.setCalories(UIUtils.getTextFromUI(findViewById(R.id.caloriesText)));
		food.setCarbs(UIUtils.getTextFromUI(findViewById(R.id.carbsText)));
		food.setSugar(UIUtils.getTextFromUI(findViewById(R.id.sugarsText)));
		food.setFiber(UIUtils.getTextFromUI(findViewById(R.id.fiberText)));
		food.setFats(UIUtils.getTextFromUI(findViewById(R.id.fatsText)));
		food.setSaturatedFats(UIUtils.getTextFromUI(findViewById(R.id.saturatedFatsText)));
		food.setProtein(UIUtils.getTextFromUI(findViewById(R.id.proteinsText)));
		food.setSodium(UIUtils.getTextFromUI(findViewById(R.id.sodiumText)));
		// save food on DB
		getHelper().getFoodDao().create(food);
		// show toast
		Toast.makeText(getApplicationContext(), R.string.newFoodInserted,
				Toast.LENGTH_SHORT).show();
		// close activity
		finish();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}