package com.gyp.pfc.activities.meal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
	
	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditMealActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
