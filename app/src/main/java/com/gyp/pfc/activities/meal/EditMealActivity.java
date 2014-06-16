package com.gyp.pfc.activities.meal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.activities.food.FoodConstants;
import com.gyp.pfc.activities.food.FoodListActivity;
import com.gyp.pfc.adapters.MealNameListViewAdapter;
import com.gyp.pfc.adapters.PortionArrayAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.Portion;
import com.gyp.pfc.dialogs.PortionQuantityDialog;
import com.gyp.pfc.dialogs.PortionQuantityDialog.PortionQuantityDialogListener;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * Activity for the edition of a meal
 * 
 * @author Alvaro
 * 
 */
public class EditMealActivity extends OrmLiteBaseActivity<DatabaseHelper> implements MealConstants, FoodConstants,
		PortionQuantityDialogListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Meal being edited on the activity */
	private Meal meal;

	/** Helper to be used */
	private MealActivityHelper h;

	/** Array adapter for the meal's portions */
	private PortionArrayAdapter portionAdapter;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new MealActivityHelper(this);
		portionAdapter = new PortionArrayAdapter(this, R.layout.portion_list_item, new ArrayList<Portion>());

		setContentView(R.layout.meal_edit_meal);
		populateSpinner();
		((ListView) findViewById(R.id.mealFoodList)).setAdapter(portionAdapter);

		// by default today's first meal
		meal = h.createMealFor(new Date(), getFirstName());
		updateMealWithDB();
		updateUI();
	}

	/**
	 * Method for the nextDate button onClick event
	 * 
	 * @param view
	 *            the button
	 */
	public void nextDate(View view) {
		moveMealDate(1);
	}

	/**
	 * Method for the nextDate button onClick event
	 * 
	 * @param view
	 *            the button
	 */
	public void previousDate(View view) {
		moveMealDate(-1);
	}

	/**
	 * Method for the deleteButton button onClick event
	 * 
	 * @param view
	 *            the button
	 */
	public void deleteButton(View view) {
		ListView exerciseList = (ListView) findViewById(R.id.mealFoodList);
		int pos = exerciseList.indexOfChild((View) view.getParent());
		final Portion portion = portionAdapter.getItem(pos);
		h.deleteWithDialog(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				doDeletePortion(portion);
			}
		});
	}

	/**
	 * Method for the addFoodtoMealButton button onClick event
	 * 
	 * @param view
	 *            the button
	 */
	public void addFoodtoMealButton(View view) {
		Intent selectFood = new Intent(getApplicationContext(), FoodListActivity.class);
		startActivityForResult(selectFood, SELECT_FOOD);
	}

	@Override
	public void onPortionQuantityDialogAccept(Portion portion) {
		// save the meal before adding if necessary
		getHelper().getMealDao().createIfNotExists(meal);
		// save the portion before adding
		getHelper().getPortionDao().create(portion);
		// add portion to meal and save
		getHelper().getMealDao().refresh(meal);
		meal.addPortion(portion);
		getHelper().getPortionDao().update(portion);
		getHelper().getMealDao().update(meal);
		updateUI();
	}

	@Override
	public void onPortionQuantityDialogCancel() {
		// NOOP
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// get selected food from intent
		Food food = (Food) data.getExtras().get(SELECTED_FOOD);
		// show dialog for entering quantity
		new PortionQuantityDialog(this, this, food).show();
	}

	// Private -------------------------------------------------------

	/**
	 * Changes the currently edited meal to the one with the specified days
	 * added
	 * 
	 * @param days
	 *            the days to add (or subtract if negative) to the current
	 *            meal's date
	 */
	private void moveMealDate(int days) {
		meal = h.createMealFor(DateUtils.addDays(meal.getDate(), days), getFirstName());
		updateMealWithDB();
		updateUI();
	}

	/**
	 * Searches for a meal with the requested data on DB and if any, sets it as
	 * the activity's meal
	 */
	private void updateMealWithDB() {
		QueryBuilder<Meal, Integer> qb = getHelper().getMealDao().queryBuilder();
		try {
			qb.where().eq("date", meal.getDate()).and().eq("name_id", meal.getName());
			Meal dbMeal = qb.queryForFirst();
			if (dbMeal != null) {
				meal = dbMeal;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
		h.setTextToUI(R.id.caloriesCell, formatQuantity(meal.getCalories()));
		h.setTextToUI(R.id.proteinCell, formatQuantity(meal.getProtein()));
		h.setTextToUI(R.id.carbsCell, formatQuantity(meal.getCarbs()));
		h.setTextToUI(R.id.fatsCell, formatQuantity(meal.getFats()));

		updatePortionsList();
	}

	/**
	 * Updates the content of the portions list with the meal's portions
	 */
	private void updatePortionsList() {
		portionAdapter.clear();
		if (meal.getId() != null) {
			getHelper().getMealDao().refresh(meal);
			for (Portion obj : meal.getPortions()) {
				portionAdapter.add(obj);
			}
		}
		portionAdapter.notifyDataSetChanged();
	}

	/**
	 * Populates the spinner with all the meal names ordered
	 */
	private void populateSpinner() {
		List<MealName> mealNames = getHelper().getMealNameDao().queryForAll();
		Collections.sort(mealNames);
		// prepare adapter for exercises list
		MealNameListViewAdapter adapter = new MealNameListViewAdapter(this, mealNames);
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

	/**
	 * Formats the passed quantity as an integer
	 * 
	 * @param quantity
	 *            the quantity to be formated
	 * @return the formatted quantity as an integer
	 */
	private String formatQuantity(Double quantity) {
		return Integer.toString(quantity.intValue());
	}

	/**
	 * Does the actual portion deletion from both the edited meal and DB
	 * 
	 * @param portion
	 *            the portion to be deleted
	 */
	private void doDeletePortion(Portion portion) {
		// remove item from list's adapter
		portionAdapter.remove(portion);
		// refresh the adapter to update UI
		portionAdapter.notifyDataSetChanged();
		// remove trainingExercise
		meal.getPortions().remove(portion);
		getHelper().getPortionDao().delete(portion);
		getHelper().getMealDao().update(meal);
		getHelper().getMealDao().refresh(meal);
		updateUI();
	}

	// Inner classes -------------------------------------------------

}
