package com.gyp.pfc.activities.meal;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.widget.Spinner;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.adapters.MealNameListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for the edition of a meal
 * 
 * @author Alvaro
 * 
 */
public class EditMealActivity extends OrmLiteBaseActivity<DatabaseHelper> implements MealConstants {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Meal being edited on the activity */
	private Meal meal;

	/** Helper to be used */
	private MealActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new MealActivityHelper(this);

		setContentView(R.layout.meal_edit_meal);
		populateSpinner();
		// by default today's first meal
		meal = new Meal();
		meal.setDate(new Date());
		meal.setName(getFirstName());
		updateUI();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Returns the first {@link MealName} of the meal name's spinner
	 * 
	 * @return the first {@link MealName} of the meal name's spinner
	 */
	private MealName getFirstName() {
		return (MealName) getMealNameSpinner().getItemAtPosition(0);
	}

	/**
	 * Fills the UI with the data from the activity's food
	 */
	private void updateUI() {
		h.setTextToUI(R.id.dateText, TimeUtils.formatDate(meal.getDate()));
		h.setTextToUI(R.id.caloriesCell, meal.getMealCalories().toString());
		h.setTextToUI(R.id.proteinCell, meal.getMealProtein().toString());
		h.setTextToUI(R.id.carbsCell, meal.getMealCarbs().toString());
		h.setTextToUI(R.id.fatsCell, meal.getMealFats().toString());
	}

	/**
	 * Populates the spinner with all the meal names ordered
	 */
	private void populateSpinner() {
		List<MealName> mealNames = getHelper().getMealNameDao().queryForAll();
		Collections.sort(mealNames);
		// prepare adapter for exercises list
		MealNameListViewAdapter adapter = new MealNameListViewAdapter(this, R.layout.meal_name_list_item,
				mealNames);
		// set adapter to spinner
		getMealNameSpinner().setAdapter(adapter);
	}

	/**
	 * Returns the meal name spinner
	 * 
	 * @return the meal name spinner
	 */
	private Spinner getMealNameSpinner() {
		return (Spinner) findViewById(R.id.mealNameSpinner);
	}
	// Inner classes -------------------------------------------------

}
