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
		MealName name1 = new MealName();
		name1.setId(1);
		name1.setName("First");
		name1.setOrder(1);
		daoNames.create(name1);
		MealName name2 = new MealName();
		name2.setId(2);
		name2.setName("Second");
		name2.setOrder(2);
		daoNames.create(name2);
		// today's String representation
		String today = DateFormatUtils.format(new Date(), "dd/MM/yyyy");
		System.out.println(today);
		// WHEN
		// activity is created
		createActivity();
		// THEN
		assertViewText(R.id.dateText, today);
		Spinner spinner = (Spinner) activity.findViewById(R.id.mealNameSpinner);
		MealName mealName = (MealName) spinner.getSelectedItem();
		assertThat(mealName, is(name1));
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
