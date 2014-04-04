package com.gyp.pfc.activities.food;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

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
	public void shouldShowFoodDetailsOnlyWithObligatoryData() {
		// GIVEN
		// a food is passed via intent to the activity
		passFoodWithOnlyObligatoryFieldsToActivity();
		// activity is created
		createActivity();
		// WHEN

		// THEN

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
		intentPassedWithFood(food);
	}

	private void passFoodWithOnlyObligatoryFieldsToActivity() {
		food = createFoodWithOnlyObligatoryFields();
		intentPassedWithFood(food);
	}

	// Inner classes -------------------------------------------------
}
