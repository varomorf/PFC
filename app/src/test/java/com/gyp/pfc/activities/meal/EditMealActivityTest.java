package com.gyp.pfc.activities.meal;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import static org.hamcrest.CoreMatchers.is;
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
import android.widget.Spinner;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.data.domain.Meal;
import com.gyp.pfc.data.domain.MealName;
import com.gyp.pfc.data.domain.Portion;

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
		// existing meal for today's first meal
		Meal meal = createMeal(null);
		Portion a = createPortion(90, 100d, 10d, 8d, 5d);
		Portion b = createPortion(50, 100d, 30d, 2d, 2d);
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
		assertViewText(R.id.carbsCell, "24");
		assertViewText(R.id.proteinCell, "8");
		assertViewText(R.id.fatsCell, "6");
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditMealActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
