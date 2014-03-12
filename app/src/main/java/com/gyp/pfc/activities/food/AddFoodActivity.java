package com.gyp.pfc.activities.food;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
		try {
			Food food = createFood();
			// save food on DB
			getHelper().getFoodDao().create(food);
			// show toast
			Toast.makeText(getApplicationContext(), R.string.newFoodInserted,
					Toast.LENGTH_SHORT).show();
			// close activity
			finish();
		} catch (IllegalArgumentException e) {
			// NOOP
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Creates a new food with the data from the add_food form
	 * 
	 * @return the food created
	 * @throws IllegalArgumentException
	 *             if a required field is not filled
	 */
	private Food createFood() throws IllegalArgumentException {
		Food food = new Food();
		// get required food data
		food.setName(getEditTextViewAsserting(R.id.foodNameText,
				R.string.foodNameHint));
		food.setCalories(getEditTextViewAsserting(R.id.caloriesText,
				R.string.caloriesHint));
		food.setCarbs(getEditTextViewAsserting(R.id.carbsText,
				R.string.carbsHint));
		food.setFats(getEditTextViewAsserting(R.id.fatsText,
				R.string.fatsHint));
		food.setProtein(getEditTextViewAsserting(R.id.proteinsText,
				R.string.proteinsHint));
		// get the rest of the data
		food.setBrandName(UIUtils
				.getTextFromUI(findViewById(R.id.foodBrandText)));
		food.setSugar(UIUtils.getTextFromUI(findViewById(R.id.sugarsText)));
		food.setFiber(UIUtils.getTextFromUI(findViewById(R.id.fiberText)));
		food.setSaturatedFats(UIUtils
				.getTextFromUI(findViewById(R.id.saturatedFatsText)));
		food.setSodium(UIUtils.getTextFromUI(findViewById(R.id.sodiumText)));
		return food;
	}

	/**
	 * Returns the value of the EditText with the passed id. If the value is
	 * blank, a hint and error will be shown for the specified string id and an
	 * IllegalArgumentException will be thrown
	 * 
	 * @param viewId
	 *            the id of the EditText
	 * @param hintId
	 *            the id of the string for the hint
	 * @return the value of the EditText
	 * @throws IllegalArgumentException
	 *             if the name is not filled
	 */
	private String getEditTextViewAsserting(int viewId, int hintId) {
		EditText et = (EditText) findViewById(viewId);
		String value = et.getText().toString();
		if (StringUtils.isBlank(value)) {
			et.setHint(hintId);
			et.setError(getString(hintId));
			throw new IllegalArgumentException();
		}
		return value;
	}

	// Inner classes -------------------------------------------------

}