package com.gyp.pfc.activities.food;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;

/**
 * Test for the {@link ShowFoodDetailsActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class ShowFoodDetailsActivityTest extends BaseFoodTest {

	// Constants -----------------------------------------------------

	public static final byte DELETE_MENU_POS = 0;
	public static final byte EDIT_MENU_POS = 1;

	// Attributes ----------------------------------------------------

	private Food food;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void clearFoodDB() {
		super.before();
		List<Food> foods = dao.queryForAll();
		dao.delete(foods);
	}

	@Test
	public void shouldAskConfirmationForDeletion() {
		// GIVEN
		// a food is passed via intent to the activity
		passFoodToActivity();
		// activity is created
		createActivity();
		// WHEN
		// menu key is pressed
		activity.pressMenuKey();
		// delete option is clicked
		TestMenu menu = TestMenu.getLastMenu();
		menu.clickOn(DELETE_MENU_POS);
		// THEN
		// question is asked for deletion
		assertAlertDialogText(R.string.assureFoodDeletion);
	}

	@Test
	public void shouldShowFoodDetails() {
		// GIVEN
		// a food is passed via intent to the activity
		passFoodToActivity();
		// WHEN
		// activity is created
		createActivity();
		// THEN
		assertViewContaining(R.id.foodDetailsName, BaseFoodTest.FOOD_NAME);
		assertViewContaining(R.id.foodDetailsName, BaseFoodTest.FOOD_BRAND);
		assertViewText(R.id.caloriesText, BaseFoodTest.FOOD_CALORIES.toString());
		assertViewText(R.id.proteinText, BaseFoodTest.FOOD_PROTEINS.toString());
		assertViewText(R.id.carbsText, BaseFoodTest.FOOD_CARBS.toString());
		assertViewText(R.id.sugarText, BaseFoodTest.FOOD_SUGAR.toString());
		assertViewText(R.id.fatsText, BaseFoodTest.FOOD_FATS.toString());
		assertViewText(R.id.saturatedFatsText, BaseFoodTest.FOOD_SATURATED_FATS.toString());
		assertViewText(R.id.fiberText, BaseFoodTest.FOOD_FIBER.toString());
		assertViewText(R.id.sodiumText, new Double(BaseFoodTest.FOOD_SODIUM * 1000).toString());
	}
	
	@Test
	public void shouldShowFoodNameWithoutBrandOrSodium() {
		// GIVEN
		food = createFood();
		food.setBrandName("");
		// a food is passed via intent to the activity
		passFoodToActivity(food);
		// WHEN
		// activity is created
		createActivity();
		// THEN
		assertViewText(R.id.foodDetailsName, BaseFoodTest.FOOD_NAME);
	}

	@Test
	public void shouldEditFoodViaMenu() {
		// GIVEN
		// a food is passed via intent to the activity
		passFoodToActivity();
		// activity is created
		createActivity();
		// WHEN
		activity.pressMenuKey();
		// edit food item is clicked
		TestMenu menu = TestMenu.getLastMenu();
		menu.clickOn(EDIT_MENU_POS);
		// THEN
		// next activity is EditFoodActivity
		Intent nextIntent = activity.getNextStartedActivity();
		assertThat(nextIntent.getComponent().getClassName(), is(EditFoodActivity.class.getName()));
		Food food = (Food) nextIntent.getSerializableExtra(EditFoodActivity.SELECTED_FOOD);
		assertThat(food, is(this.food));
	}

	@Test
	public void shouldRefreshViiewAfterEdition() {
		// GIVEN
		String finalName = "Nueva";
		// a food is passed via intent to the activity
		passFoodToActivity();
		// activity is created
		createActivity();
		// expected food name
		assertViewContaining(R.id.foodDetailsName, BaseFoodTest.FOOD_NAME);
		activity.pressMenuKey();
		// edit food item is clicked
		TestMenu menu = TestMenu.getLastMenu();
		menu.clickOn(EDIT_MENU_POS);
		Intent editionItent = activity.getNextStartedActivity();
		// edition is performed
		editFoodName(BaseFoodTest.FOOD_NAME, finalName);
		dao.refresh(food);
		// WHEN
		// return from edition
		activity.receiveResult(editionItent, Activity.RESULT_OK, intentWithFood(food));
		// THEN
		// edited food name on pos 0
		assertViewContaining(R.id.foodDetailsName, finalName);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ShowFoodDetailsActivity();
	}

	// Private -------------------------------------------------------

	private void passFoodToActivity() {
		food = createFood();
		passFoodToActivity(food);
	}
	
	private void passFoodToActivity(Food food){
		intentPassedWithFood(food);
	}

	// Inner classes -------------------------------------------------
}
