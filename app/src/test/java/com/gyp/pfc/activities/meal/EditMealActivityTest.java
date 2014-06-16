package com.gyp.pfc.activities.meal;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Spinner;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.Portion;
import com.gyp.pfc.data.domain.builder.FoodBuilder;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;

/**
 * Test for {@link EditMealActivity}
 * 
 * @author alfergon
 * 
 */
@RunWith(CustomTestRunner.class)
public class EditMealActivityTest extends BaseMealTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
		List<Meal> meals = dao.queryForAll();
		dao.delete(meals);
	}

	@Test
	public void shouldCreateNewMealForTodayAndFirstMealName() {
		// GIVEN
		// no meals
		// two mealNames
		createMeal(2, "Second", 2);
		// today's String representation
		String today = DateFormatUtils.format(new Date(), "dd/MM/yyyy");
		// WHEN
		// activity is created
		createActivity();
		// THEN
		// date is correctly set
		assertViewText(R.id.dateText, today);
		// first meal is selected
		Spinner spinner = (Spinner) activity.findViewById(R.id.mealNameSpinner);
		MealName mealName = (MealName) spinner.getSelectedItem();
		assertThat(mealName, is(firstName));
		// nutrition values are correctly filled
		assertViewText(R.id.caloriesCell, "0");
		assertViewText(R.id.proteinCell, "0");
		assertViewText(R.id.carbsCell, "0");
		assertViewText(R.id.fatsCell, "0");
	}

	@Test
	public void shouldUseExistingMealIfExisting() throws SQLException {
		// GIVEN
		// Broccoli food
		Food f = new FoodBuilder().name("Broccoli").calories(100d).carbs(10d).protein(2d).getFood();
		// a portion of 90 grams of broccoli
		Portion a = createPortion(90, f);
		// Chicken breast food
		Food g = new FoodBuilder().name("Chicken breast").calories(100d).carbs(5d).protein(25d).fats(2d).getFood();
		// a portion of 50 grams of chicken breast
		Portion b = createPortion(50, g);
		// existing meal for today's first meal with the created portions
		Meal meal = createMeal(null);
		meal.addPortion(a);
		meal.addPortion(b);
		meal.getPortions().updateAll();
		dao.update(meal);
		// WHEN
		// activity is created
		createActivity();
		// THEN
		// correct meal is selected
		Spinner spinner = (Spinner) activity.findViewById(R.id.mealNameSpinner);
		MealName mealName = (MealName) spinner.getSelectedItem();
		assertThat(mealName, is(firstName));
		// nutrition values are correctly filled
		assertViewText(R.id.caloriesCell, "140");
		assertViewText(R.id.carbsCell, "12");
		assertViewText(R.id.proteinCell, "14");
		assertViewText(R.id.fatsCell, "1");
		// portions are listed
		assertItemText(getItemFromListView(0, R.id.mealFoodList), R.id.portionQuantity, a.getQuantity().toString());
		assertItemText(getItemFromListView(0, R.id.mealFoodList), R.id.portionName, f.getName());
		assertItemText(getItemFromListView(1, R.id.mealFoodList), R.id.portionQuantity, b.getQuantity().toString());
		assertItemText(getItemFromListView(1, R.id.mealFoodList), R.id.portionName, g.getName());
	}

	@Test
	public void shouldGoToNextDateWithRightArrowButton() throws SQLException {
		// GIVEN
		// a meal name
		// a meal for the first meal name for today and another one for tomorrow
		Meal meal = createMeal(null);
		Portion a = createPortion(100, 100d, 0d, 0d, 0d);
		meal.addPortion(a);
		meal.getPortions().updateAll();
		dao.update(meal);
		Date tomorrow = DateUtils.addDays(new Date(), 1);
		Meal tomorrowMeal = createMeal(tomorrow);
		Portion b = createPortion(100, 200d, 0d, 0d, 0d);
		tomorrowMeal.addPortion(b);
		tomorrowMeal.getPortions().updateAll();
		dao.update(tomorrowMeal);
		// activity is created
		createActivity();
		assertViewText(R.id.caloriesCell, "100");
		// WHEN
		// next date button is pressed
		clickOn(activity.findViewById(R.id.nextDateButton));
		// THEN
		// date's text changed to tomorrow's
		assertViewText(R.id.dateText, TimeUtils.formatDate(tomorrow));
		// tomorrow's meal has been used (using the calories as mark)
		assertViewText(R.id.caloriesCell, "200");
	}

	@Test
	public void shouldGoToNextDateWithRightArrowButtonAndCreateANewMeal() throws SQLException {
		// GIVEN
		// a meal name
		// tomorrow
		Date tomorrow = DateUtils.addDays(new Date(), 1);
		// a meal for the first meal name for today
		Meal meal = createMeal(null);
		Portion a = createPortion(100, 100d, 0d, 0d, 0d);
		meal.addPortion(a);
		meal.getPortions().updateAll();
		dao.update(meal);
		// activity is created
		createActivity();
		assertViewText(R.id.caloriesCell, "100");
		// WHEN
		// next date button is pressed
		clickOn(activity.findViewById(R.id.nextDateButton));
		// THEN
		// date's text changed to tomorrow's
		assertViewText(R.id.dateText, TimeUtils.formatDate(tomorrow));
		// tomorrow's meal has been used (using the calories as mark)
		assertViewText(R.id.caloriesCell, "0");
	}

	@Test
	public void shouldGoToPreviousDateWithLeftArrowButton() throws SQLException {
		// GIVEN
		// a meal name
		// a meal for the first meal name for today and another one for tomorrow
		Meal meal = createMeal(null);
		Portion a = createPortion(100, 100d, 0d, 0d, 0d);
		meal.addPortion(a);
		meal.getPortions().updateAll();
		dao.update(meal);
		Date yesterday = DateUtils.addDays(new Date(), -1);
		Meal tomorrowMeal = createMeal(yesterday);
		Portion b = createPortion(100, 200d, 0d, 0d, 0d);
		tomorrowMeal.addPortion(b);
		tomorrowMeal.getPortions().updateAll();
		dao.update(tomorrowMeal);
		// activity is created
		createActivity();
		assertViewText(R.id.caloriesCell, "100");
		// WHEN
		// next date button is pressed
		clickOn(activity.findViewById(R.id.previousDateButton));
		// THEN
		// date's text changed to tomorrow's
		assertViewText(R.id.dateText, TimeUtils.formatDate(yesterday));
		// tomorrow's meal has been used (using the calories as mark)
		assertViewText(R.id.caloriesCell, "200");
	}

	@Test
	public void shouldGoToPreviousDateWithLeftArrowButtonAndCreateANewMeal() throws SQLException {
		// GIVEN
		// a meal name
		// tomorrow
		Date yesterday = DateUtils.addDays(new Date(), -1);
		// a meal for the first meal name for today
		Meal meal = createMeal(null);
		Portion a = createPortion(100, 100d, 0d, 0d, 0d);
		meal.addPortion(a);
		meal.getPortions().updateAll();
		dao.update(meal);
		// activity is created
		createActivity();
		assertViewText(R.id.caloriesCell, "100");
		// WHEN
		// next date button is pressed
		clickOn(activity.findViewById(R.id.previousDateButton));
		// THEN
		// date's text changed to tomorrow's
		assertViewText(R.id.dateText, TimeUtils.formatDate(yesterday));
		// tomorrow's meal has been used (using the calories as mark)
		assertViewText(R.id.caloriesCell, "0");
	}

	@Test
	public void shouldRemovePortionsFromAMealWithTheDeleteButton() throws SQLException {
		// GIVEN
		// Broccoli food
		Food f = new FoodBuilder().name("Broccoli").getFood();
		// a portion of 90 grams of broccoli
		Portion a = createPortion(90, f);
		// Chicken breast food
		Food g = new FoodBuilder().name("Chicken breast").getFood();
		// a portion of 50 grams of chicken breast
		Portion b = createPortion(50, g);
		// existing meal for today's first meal with the created portions
		Meal meal = createMeal(null);
		meal.addPortion(a);
		meal.addPortion(b);
		meal.getPortions().updateAll();
		dao.update(meal);
		// activity is created
		createActivity();
		assertListSize(2, R.id.mealFoodList);
		// WHEN
		clickOnListItemButton(R.id.mealFoodList, 1, R.id.deleteButton);
		// THEN
		// question is asked for deletion
		assertAlertDialogText(R.string.assurePortionDeletion);
		// WHEN
		// affirmative button is clicked on
		clickOn(ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE));
		// THEN
		// only one portion left
		assertListSize(1, R.id.mealFoodList);
		// meal only has one portion
		dao.refresh(meal);
		assertEquals("Meal doesn't have the expected number of portions", 1, meal.getPortions().size());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditMealActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
