package com.gyp.pfc.activities.food;

import org.apache.commons.lang.StringUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
				R.string.foodNameError));
		food.setCalories(getEditTextViewAsserting(R.id.caloriesText,
				R.string.caloriesError));
		food.setProtein(getEditTextViewAsserting(R.id.proteinsText,
				R.string.proteinsError));
		food.setCarbs(getEditTextViewAsserting(R.id.carbsText,
				R.string.carbsError));
		food.setFats(getEditTextViewAsserting(R.id.fatsText,
				R.string.fatsError));
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
	 * blank, an error will be shown for the specified string id and an
	 * IllegalArgumentException will be thrown
	 * 
	 * @param viewId
	 *            the id of the EditText
	 * @param errorId
	 *            the id of the string for the error
	 * @return the value of the EditText
	 * @throws IllegalArgumentException
	 *             if the name is not filled
	 */
	private String getEditTextViewAsserting(int viewId, int errorId) {
		EditText et = (EditText) findViewById(viewId);
		String value = et.getText().toString();
		if (StringUtils.isBlank(value)) {
			et.requestFocus();
			et.setError(getString(errorId));
			throw new IllegalArgumentException();
		}
		return value;
	}

	// Inner classes -------------------------------------------------

}