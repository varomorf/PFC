/**
 * 
 */
package com.gyp.pfc.activities.meal;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.data.domain.Meal;

/**
 * Tests for the {@link MealListActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class MealListActivityTest extends BaseMealTest {

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
	public void shouldShowDaysFromTodayToPreviousDatesUntilLastMeal() {
		// GIVEN
		// expected dates
		String today = TimeUtils.formatDate(new Date());
		String tomorrow = TimeUtils.formatDate(DateUtils.addDays(new Date(), 1));
		String date15DaysLater = TimeUtils.formatDate(DateUtils.addDays(new Date(), 15));
		// one meal from 15 days ago
		Meal meal = new Meal();
		meal.setDate(DateUtils.addDays(new Date(), 15));
		meal.setName(firstName);
		dao.create(meal);
		// WHEN
		// activity is created
		createActivity();
		// THEN
		// there should be 16 dates
		assertListSize(16);
		assertItemText(getItemFromListView(0, android.R.id.list), android.R.id.text1, today);
		assertItemText(getItemFromListView(1, android.R.id.list), android.R.id.text1, tomorrow);
		assertItemText(getItemFromListView(15, android.R.id.list), android.R.id.text1, date15DaysLater);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new MealListActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
