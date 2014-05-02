package com.gyp.pfc.activities.food;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivity;
import com.gyp.pfc.data.domain.Food;

/**
 * <p>
 * Activity for entering nutritional and identification data can be entered for
 * a food in order to save it for later use.
 * </p>
 * 
 * @author Alvaro
 * 
 */
public class AddFoodActivity extends BaseActivity {

	// Constants -----------------------------------------------------

	public static final int FOOD_NAME = 1;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		// get required food data
		food.setName(getEditTextViewAsserting(R.id.foodNameText, R.string.foodNameError));
		food.setCalories(getEditTextViewAsserting(R.id.caloriesText, R.string.caloriesError));
		food.setProtein(getEditTextViewAsserting(R.id.proteinsText, R.string.proteinsError));
		food.setCarbs(getEditTextViewAsserting(R.id.carbsText, R.string.carbsError));
		food.setFats(getEditTextViewAsserting(R.id.fatsText, R.string.fatsError));
		// get the rest of the data
		food.setBrandName(getTextFromUI(R.id.foodBrandText));
		food.setSugar(getTextFromUI(R.id.sugarText));
		food.setFiber(getTextFromUI(R.id.fiberText));
		food.setSaturatedFats(getTextFromUI(R.id.saturatedFatsText));
		food.setSodium(getTextFromUI(R.id.sodiumText));
		return food;
	}

	/**
	 * Returns the value of the EditText with the passed id. If the value is
	 * blank, an error will be shown for the specified string id and an
	 * IllegalArgumentException will be thrown
	 * 
	 * @param viewId
	 *            the id of the EditText
	 * @param errorId
	 *            the id of the string for the error
	 * @return the value of the EditText
	 * @throws MandatoryFieldNotFilledException
	 *             if the name is not filled
	 */
	private String getEditTextViewAsserting(int viewId, int errorId) {
		String value = getTextFromUI(viewId);
		if (StringUtils.isBlank(value)) {
			throw new MandatoryFieldNotFilledException(viewId, getString(errorId));
		}
		return value;
	}

	// Inner classes -------------------------------------------------

}