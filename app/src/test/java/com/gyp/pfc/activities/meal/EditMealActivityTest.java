package com.gyp.pfc.activities.meal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.widget.Spinner;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
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
		MealName firstName = createMeal(1, "First", 1);
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
		// a meal name
		MealName firstName = createMeal(1, "First", 1);
		// existing meal for today's first meal
		Meal meal = new Meal();
		meal.setDate(new Date());
		meal.setName(firstName);
		dao.create(meal);
		dao.refresh(meal);
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

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditMealActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
