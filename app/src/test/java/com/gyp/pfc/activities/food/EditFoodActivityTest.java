/**
 * 
 */
package com.gyp.pfc.activities.food;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.food.Food;

/**
 * Tests for the {@link EditFoodActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class EditFoodActivityTest extends BaseFoodTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldHaveFoodDataPreLoaded() {
		// GIVEN
		// a food is passed via intent to the activity
		passFoodToActivity();
		// WHEN
		// activity is created
		createActivity();
		// THEN
		assertViewText(R.id.foodNameText, BaseFoodTest.FOOD_NAME);
		assertViewText(R.id.foodBrandText, BaseFoodTest.FOOD_BRAND);
		assertViewText(R.id.caloriesText, BaseFoodTest.FOOD_CALORIES.toString());
		assertViewText(R.id.proteinsText, BaseFoodTest.FOOD_PROTEINS.toString());
		assertViewText(R.id.carbsText, BaseFoodTest.FOOD_CARBS.toString());
		assertViewText(R.id.sugarText, BaseFoodTest.FOOD_SUGAR.toString());
		assertViewText(R.id.fatsText, BaseFoodTest.FOOD_FATS.toString());
		assertViewText(R.id.saturatedFatsText, BaseFoodTest.FOOD_SATURATED_FATS.toString());
		assertViewText(R.id.fiberText, BaseFoodTest.FOOD_FIBER.toString());
		assertViewText(R.id.sodiumText, BaseFoodTest.FOOD_SODIUM.toString());
	}

	@Test
	public void shouldEditTheFoodAndReturnIntent() {
		// GIVEN
		String newName = "newName";
		// a food is passed via intent to the activity
		passFoodToActivity();
		// activity is created
		createActivity();
		// WHEN
		// edit data
		enterText(R.id.foodNameText, newName);
		// press commit button
		clickOn(activity.findViewById(R.id.commitButton));
		// THEN
		Food food = dao.queryForId(FOOD_ID);
		assertEquals("Food name should have been edited", newName, food.getName());
		// result is OK
		assertEquals("Result was not OK", Activity.RESULT_OK, activity.getResultCode());
		// activity is finished
		assertTrue(activity.isFinishing());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditFoodActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
